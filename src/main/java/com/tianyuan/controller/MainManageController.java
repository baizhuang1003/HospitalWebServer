package com.tianyuan.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tianyuan.bean.DeptBean;
import com.tianyuan.bean.LimitBean;
import com.tianyuan.bean.RoleBean;
import com.tianyuan.bean.SmsBean;
import com.tianyuan.bean.UserBean;
import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.Common;
import com.tianyuan.core.EasyTree;
import com.tianyuan.core.EntityPager;
import com.tianyuan.core.SMSHelper;
import com.tianyuan.model.UserModel;
import com.tianyuan.repository.DeptRepository;
import com.tianyuan.repository.LimitRepository;
import com.tianyuan.repository.OrganizationRepository;
import com.tianyuan.repository.RoleRepository;
import com.tianyuan.repository.SmsRepository;
import com.tianyuan.repository.UserRepository;


@RestController
public class MainManageController extends BaseManageController {

	@Autowired
	UserRepository memberRepository;
	@Autowired
	OrganizationRepository organizationRepository;
	@Autowired
	LimitRepository limitRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	DeptRepository deptRepository;
	@Autowired
	SmsRepository smsRepository;
	
	@GetMapping("main/index")
	public ModelAndView index() {
		Initialize();
		ModelAndView model = new ModelAndView();
		if(this.getUserId()==null || this.getUserId().equals("") || this.getUserId().equals("0")) {
			model.setViewName("redirect:/manage/login/index");
			return model;
		}
		UserModel entity = new UserModel();
		entity.setUser(memberRepository.selectRow(" id='"+this.getUserId()+"' "));
		entity.setOrgan(organizationRepository.selectRow(" code='"+this.getCode()+"'"));
		entity.setLimits(getLimits());
		DeptBean dept = deptRepository.selectRow(" id='"+entity.getUser().getDeptid()+"'");
		if(dept==null) dept = new DeptBean();
		entity.setDept(dept);
		String systemcode = "";
		if (System_Organ_Level == 1) systemcode = this.getCode().substring(0, 2) + "0000";
		else if (System_Organ_Level == 2) systemcode = this.getCode().substring(0, 4) + "00";
		entity.setSystemname(organizationRepository.selectRow(" code='"+systemcode+"'").getName());
		model.addObject("entity", entity);
		model.setViewName(prefix+"/main/index");
		return model;
	}
	
	private List<LimitBean> getLimits()
    {
        UserBean member = memberRepository.selectRow(" id='"+this.getUserId()+"' ");
        List<LimitBean> list = new ArrayList<LimitBean>();
        if (this.System_Organ_Level == 1)//系统为省级
        {
            if (member.getIsmanager()==1)
            {
                String where = "isspecial = 0";
                if (this.getOrganLevel() == 3)//限制县级管理员
                {
                    where += " and id not in(300101,300102,300103,300104,300105,300106,300107,100105,100101,200104,200105)";
                }
                if (this.getOrganLevel() == 2)//限制市级管理员
                {
                    where += " and id not in(300101,300102,300103,300104,300105,300106,300107,100105,100108)";
                }
                list.addAll(limitRepository.selectAll(where, "sort"));
            }
            else
            {
                RoleBean role = roleRepository.selectRow(" id='"+member.getRoleid()+"'");
                StringBuilder sb = new StringBuilder();
                if (role==null) role = new RoleBean();
                if(role.getLimits().equals("")) role.setLimits("");
                    
                String[] arrString = role.getLimits().split(",");
                for (String item : arrString)
                {
                    if (item==null || item.equals("")) continue;
                    //屏蔽区县级 权限 组织机构
                    if (this.getOrganLevel() == 3 && item == "100101") continue;
                    //屏蔽县级 号牌管理
                    if (this.getOrganLevel() == 3 && item == "200104") continue;
                    //屏蔽县级 照牌取消功能
                    if (this.getOrganLevel() == 3 && item == "200105") continue;
                    //省级用户拥有数据字典
                    if (this.getOrganLevel() != 1 && item == "100105") continue;
                    //省级用户可以维护证书系统
                    if (this.getOrganLevel() != 1 && item == "3001") continue;
                    sb.append(",");
                    sb.append(item);
                }
                String ids = "";
                if (sb.length() > 0) ids = sb.toString().substring(1);

                String where = "id in (" + ids + ")";
                where += " and  isspecial =0";

                list.addAll(limitRepository.selectAll(where, "sort"));
            }
        }
        else if (this.System_Organ_Level == 2)//系统为市级
        {
            if (member.getIsmanager()==1)
            {
                String where = "isspecial = 0";
                if (this.getOrganLevel() == 3)//限制县级管理员
                {
                    where += " and id not in(300101,300102,300103,300104,300105,300106,300107,100105,100101,200104,200105)";
                }
                System.out.println(where);
                list.addAll(limitRepository.selectAll(where, "sort"));
            }
            else
            {
            	RoleBean role = roleRepository.selectRow(" id='"+member.getRoleid()+"'");
                StringBuilder sb = new StringBuilder();
                if (role==null) role = new RoleBean();
                if(role.getLimits().equals("")) role.setLimits("");
                String[] arrString = role.getLimits().split(",");
                for (String item : arrString)
                {
                	if (item==null || item.equals("")) continue;
                    //屏蔽区县级 权限 组织机构
                    if (this.getOrganLevel() == 3 && item == "100101") continue;
                    //屏蔽县级 号牌管理
                    if (this.getOrganLevel() == 3 && item == "200104") continue;
                    //屏蔽县级 照牌取消功能
                    if (this.getOrganLevel() == 3 && item == "200105") continue;
                    sb.append(",");
                    sb.append(item);
                }

                String ids = "";
                if (sb.length() > 0) ids = sb.toString().substring(1);
                String where ="id in (" + ids + ")";
                where += " and  isspecial =0";
                list.addAll(limitRepository.selectAll(where, "sort"));
            }
        }
        return list;
    }
	
	//工作台
	@GetMapping("main/workbench")
	public ModelAndView workbench() {
		ModelAndView model = new ModelAndView();
//		cmsstation station = cmsstationBuss.SelectRow(this.ServerDomain);
//        station.welmanager = this.ServerScheme + "://" + this.ServerDomain + station.welmanager;
		model.addObject("welmanager", this.getServerScheme() + "://" + this.getServerDomain()+"/images/logo.png");
		model.setViewName(prefix+"/main/workbench");
		return model;
	}
	
	//权限列表
	@GetMapping("main/getusertree")
	public List<EasyTree> GetUserTree(){
		 UserBean member = memberRepository.selectRow(" id='"+this.getUserId()+"' ");
        List<LimitBean> list = new ArrayList<LimitBean>();
        if (this.System_Organ_Level == 1)//系统为省级
        {
            if (member.getIsmanager()==1)
            {
                String where = "isspecial = 0";
                if (this.getOrganLevel() == 3)//限制县级管理员
                {
                    where += " and id not in(300101,300102,300103,300104,300105,300106,300107,100105,100101,200104,200105)";
                }
                if (this.getOrganLevel() == 2)//限制市级管理员
                {
                    where += " and id not in(300101,300102,300103,300104,300105,300106,300107,100105,100108)";
                }
                list.addAll(limitRepository.selectAll(where, "sort"));
            }
            else
            {
            	RoleBean role = roleRepository.selectRow(" id='"+member.getRoleid()+"'");
                StringBuilder sb = new StringBuilder();
                if (role==null) role = new RoleBean();
                if(role.getLimits().equals("")) role.setLimits("");
                String[] arrString = role.getLimits().split(",");
                for (String item : arrString)
                {
                	if (item==null || item.equals("")) continue;
                    //屏蔽区县级 权限 组织机构
                    if (this.getOrganLevel() == 3 && item == "100101") continue;
                    //屏蔽县级 号牌管理
                    if (this.getOrganLevel() == 3 && item == "200104") continue;
                    //屏蔽县级 照牌取消功能
                    if (this.getOrganLevel() == 3 && item == "200105") continue;
                    //省级用户拥有数据字典
                    if (this.getOrganLevel() != 1 && item == "100105") continue;
                    //省级用户可以维护证书系统
                    if (this.getOrganLevel() != 1 && item == "3001") continue;
                    sb.append(",");
                    sb.append(item);
                }
                String ids = "";
                if (sb.length() > 0) ids = sb.toString().substring(1);

                String where = "id in (" + ids + ")";
                where += " and  isspecial =0";

                list.addAll(limitRepository.selectAll(where, "sort"));
            }
        }
        else if (this.System_Organ_Level == 2)//系统为市级
        {
            if (member.getIsmanager()==1)
            {
                list.addAll(limitRepository.selectAll("isspecial = 0", "sort"));
            }
            else
            {
            	RoleBean role = roleRepository.selectRow(" id='"+member.getRoleid()+"'");
                StringBuilder sb = new StringBuilder();
                if (role==null) role = new RoleBean();
                if(role.getLimits().equals("")) role.setLimits("");
                String[] arrString = role.getLimits().split(",");
                for (String item : arrString)
                {
                	if (item==null || item.equals("")) continue;
                    ////屏蔽区县级 权限 组织机构
                    //if (this.OrganLevel == 3 && item == "100101") continue;
                    ////屏蔽县级 号牌管理
                    //if (this.OrganLevel == 3 && item == "200104") continue;
                    ////屏蔽县级 照牌取消功能
                    //if (this.OrganLevel == 3 && item == "200105") continue;
                    ////省级用户拥有数据字典
                    //if (this.OrganLevel != 1 && item == "100105") continue;
                    ////省级用户可以维护证书系统
                    //if (this.OrganLevel != 1 && item == "3001") continue;
                    sb.append(",");
                    sb.append(item);
                }

                String ids = "";
                if (sb.length() > 0) ids = sb.toString().substring(1);
                String where = "id in (" + ids + ")";
                where += " and  isspecial =0";
                list.addAll(limitRepository.selectAll(where, "sort"));
            }
        }
        List<EasyTree> result = new ArrayList<EasyTree>();
        result.addAll(CreateChildTree(list, 0));
        return result;
	}
	
	private List<EasyTree> CreateChildTree(List<LimitBean> list, int parentid)
    {
        List<LimitBean> temp = new ArrayList<LimitBean>();
        for (LimitBean item : list) {
			if(item.getParentid()==parentid) temp.add(item);
		}
        List<EasyTree> result = new ArrayList<EasyTree>();
        for (LimitBean item : temp)
        {
            EasyTree model = new EasyTree();
            model.setAttributes(item.getUrl());
            model.setId(item.getId()+"");
            model.setText(item.getName());
            model.setChildren(CreateChildTree(list,item.getId()));
            if (item.getParentid() == 0)
                model.setIconCls("icon-layout");
            else
            	model.setIconCls("icon-gears");
           // if (model.getChildren().size() > 0)
           //     model.setState("closed");
            result.add(model);
        }
        return result;
    }
	
	//修改密码
	@GetMapping("main/editpwd")
	public ModelAndView editPwd() {
		ModelAndView model = new ModelAndView();
		model.setViewName(prefix+"/main/editpwd");
		return model;
	}
	
	@PostMapping("main/editpwd")
	public AjaxResult editPwd(String oldpwd,String newpwd,String compwd) {
		if (oldpwd==null || oldpwd.equals("")) return onFailed("请输入旧密码");
        if (newpwd==null || newpwd.equals("")) return onFailed("请输入新密码");
        if (compwd==null || compwd.equals("")) return onFailed("请确认新密码");
        if (!newpwd.equals(compwd)) return onFailed("新密码与确认密码不一致");

        UserBean entity = memberRepository.selectRow(" id='"+this.getUserId()+"'");
        if (!Common.md5Encode(oldpwd).equals(entity.getPassword())) return onFailed("旧密码错误");
        entity.setPassword(Common.md5Encode(newpwd));
        entity.setUpdateuid(this.getUserId());
        try {
        	memberRepository.updateRow(entity);
        	return onSuccess("密码修改成功");
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("密码修改失败");
		}
	}
	
	@PostMapping("main/getsmscode")
	public AjaxResult getSmsCode(String mobile) {
		if (mobile==null || mobile.equals("")) return onFailed("请输入手机号码");
		smsRepository.deleteExpired();
		SmsBean sms = smsRepository.selectRowByMobile(mobile);
        if (sms != null && sms.getId()!=null && !sms.getId().equals("")) return onFailed("短信验证码已发送");
        String code = Common.randomNumber(6);
        String[] arrSms = Common.domainInfo("akgcjx.cn").split(",");
        if(arrSms.length!=3)return onFailed("短信平台信息验证失败");
        String username = arrSms[0];
        if(username==null || username.equals(""))return onFailed("短信平台信息验证失败");
        String password = arrSms[1];
        if(password==null || password.equals(""))return onFailed("短信平台信息验证失败");
        String moduleid=arrSms[2];
        if(moduleid==null || moduleid.equals(""))return onFailed("短信平台信息验证失败");
        try {
        	if (SMSHelper.Send(username, password, moduleid,mobile, code)) {
        		SmsBean entity = new SmsBean();
        		entity.setCode(code);
        		entity.setMobile(mobile);
        		smsRepository.insertRow(entity);
        		return onSuccess("短信验证码已发送");
        	}
        	else
        	{
        		return onFailed("短信验证码发送失败");
        	}
		} catch (Exception e) {
			// TODO: handle exception
			return onFailed("短信验证码发送失败");
		}
	}
	
	@PostMapping("main/verifysms")
	public AjaxResult verifySms(String mobile, String code) {
		SmsBean sms = smsRepository.selectRowByMobile(mobile);
        if (sms == null || sms.getId()==null || sms.getId().equals("")) return onFailed("验证码已过期");
        if (sms.getCode().toUpperCase() != code.toUpperCase()) return onFailed("验证码不正确");
        return onSuccess("ok");
	}
	
	
}
