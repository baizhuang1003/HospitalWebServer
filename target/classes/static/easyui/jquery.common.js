/**
 * 
 */
//弹框显示 icon提供 error info question warning
function messageAlert(title, msg, icon) {
    if (icon)
        $.messager.alert(title, msg, icon);
    else
        $.messager.alert(title, msg);
}

/**
 * 
 */
//初始化打印1
function initWS() {
    ws = new WebSocket("ws://127.0.0.1:7778/Print");// localhost   127.0.0.1
    ws.onopen = function (e) {
        console.log("Openened connection to websocket");
        console.log(e);
        wsconnectioned = true;
    };
    ws.onclose = function () {
        console.log("Close connection to websocket");
        wsconnectioned = false;
        // 断线重连
        initWS();
    };
    ws.onmessage = function (e) {
        //收到信息
        var result = JSON.parse(e.data);
        //正在启动本地视频
        if (result.state == 102) {
            messageAlert('提示', result.msg, 'info');
        }
        //返回抓拍结果
        if (result.state == 101) {
            setLocalCapture(result.msg);
        }
        
    };
}
//发送json
function wsSendJSON(json) {
    ws.send(JSON.stringify(json));
}
//发送数据
function wsSend(state, content,msg) {
    ws.send(JSON.stringify({ state: state, content: content,msg:msg }));
}

//本地抓拍
var localUploadElemnet;
function getLocalCapture(obj) {
    if (!wsconnectioned) {
        messageAlert('提示', '请确认打印组件已经打开?', 'question');
        return;
    }

    localUploadElemnet = obj;
    wsSend(101, null, getLocationHost());
}
//本地抓拍返回调用
function setLocalCapture(url) {
    if(localUploadElemnet)
        $(localUploadElemnet).parent().parent().parent().find('img').attr('src', url);
    localUploadElemnet = null;
}

var dlg_selector;
function vehNumberSelector(callback) {
    $('body').remove('#selector-dlg');
    $('body').append('<div id="selector-dlg" style="width:100%; height:100%;"></div>');
    dlg_selector = $('#selector-dlg').dialog({
        closed: true,
        modal: true,
        cache: false,
        iconCls: 'icon-menu',
        buttons: [{
            text: '确定',
            iconCls: 'icon-ok',
            handler: function () {
                var row = gridSelector.datagrid('getSelected');
                if (row) {
                    callback(row);
                    dlg_selector.dialog('close');
                    return;
                }
                messageAlert('提示', '请选择牌照', 'warning');
            }
        }, {
            text: '取消',
            iconCls: 'icon-cancel',
            handler: function () {
                dlg_selector.dialog('close');
            }
        }]
    });

    var url = '/manage/vehicle/vehiclenumber/selector';
    dlg_selector.dialog({ href: url, title: '选择牌照' }).dialog('open');

}
function vehMemberSelector(callback) {
    $('body').remove('#selector-dlg');
    $('body').append('<div id="selector-dlg" style="width:100%; height:100%;"></div>');
    dlg_selector = $('#selector-dlg').dialog({
        closed: true,
        modal: true,
        cache: false,
        iconCls: 'icon-menu',
        buttons: [{
            text: '确定',
            iconCls: 'icon-ok',
            handler: function () {
                var row = gridSelector.datagrid('getSelected');
                if (row) {
                    callback(row);
                    dlg_selector.dialog('close');
                    return;
                }
                messageAlert('提示', '请选择业务员', 'warning');
            }
        }, {
            text: '取消',
            iconCls: 'icon-cancel',
            handler: function () {
                dlg_selector.dialog('close');
            }
        }]
    });

    var url = '/manage/base/member/selector';
    dlg_selector.dialog({ href: url, title: '选择业务员' }).dialog('open');

}
//获取当前网址
function getLocationHost() {
    var protocol = window.location.protocol;
    var host = window.location.host;
    var port = window.location.port;
    var url = protocol + '//' + host;

    return url;
    //if (port && port != '') return url + ":" + port;
    //else return url;
}

$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};


var postDownLoadFile = function (options) {
    var config = $.extend(true, { method: 'post' }, options);
    var $iframe = $('<iframe id="down-file-iframe" />');
    var $form = $('<form target="down-file-iframe" method="' + config.method + '" />');
    $form.attr('action', config.url);
    for (var key in config.data) {
        $form.append('<input type="hidden" name="' + key + '" value="' + config.data[key] + '" />');
    }
    $iframe.append($form);
    $(document.body).append($iframe);
    $form[0].submit();
    $iframe.remove();
}

//工程机械查询
function showQueryBox() {
    $('#logobox').show();
    $('#querybox').show();
}
function hideQueryBox() {
    $('#logobox').hide();
    $('#querybox').hide();
}
function hideResultBox() {
    $('#resultbox').hide();
}
function showResultBox() {
    $('#resultbox').show();
}
//清除coolie
function clearCookie() {
    var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
    if (keys) {
        for (var i = keys.length; i--;)
            document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString()
    }
}