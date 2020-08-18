package com.tianyuan.controller;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tianyuan.bean.RoleBean;
import com.tianyuan.bean.UserBean;
import com.tianyuan.core.AjaxResult;
import com.tianyuan.core.CookieHelper;
import com.tianyuan.repository.RoleRepository;
import com.tianyuan.repository.SmsRepository;
import com.tianyuan.repository.UserRepository;


@Controller
@RequestMapping("manage")
public class BaseManageController {

	@Autowired
	HttpServletRequest req;
	@Autowired
	HttpServletResponse res;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	SmsRepository smsRepository;
	
	@Value("${file.upload.liunx}")
	String linuxFilePath;
	@Value("${file.upload.windows}")
	String windowsFilePath;
	@Value("${cookie.usercookie}")
	public String usercookie;
	@Value("${cookie.codecookie}")
	public String codecookie;
	@Value("${cookie.smscode}")
	public String smscode;
	@Value("${cookie.user.age}")
	public int cookie_user_age;
	
	
	public static final String prefix="manage";

	protected File getRealFile(String path) {
		String filePath = "";
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("win"))
			filePath = windowsFilePath;
		else if (os.toLowerCase().contains("mac"))
			filePath = System.getProperty("user.home") + "/constmch/";
		else
			filePath = linuxFilePath;

		File file = new File(filePath, path);
		if (!os.toLowerCase().startsWith("win"))
			file.setWritable(true, false);

		if (!file.exists()) {
			File fileParent = file.getParentFile();
			if (!os.toLowerCase().startsWith("win"))
				fileParent.setWritable(true, false);
			fileParent.mkdirs();
		}
		return file;
	}
	protected void addCookie(String name,String value) {
		CookieHelper.addCookie(res, name, cookie_user_age, value);
	}
	
	protected String getCookieValue(String name) {
		return CookieHelper.getStringValue(req, name);
	}
	
	protected void removeCookie(String name) {
		CookieHelper.removeCookie(req, res, name);
	}
	
	/**
	 * 系统设定 级别
	 */
	protected int System_Organ_Level = 2;//系统级别 1 省 2市
	/**
	 * 机动车号牌前缀
	 * @param code
	 * @return
	 */
	public String VehiclePrex(String code)
    {
        if (code==null || code.equals("") || code.length() < 6) return "";
        else if (code.substring(0, 6) == "610403") return "V";//杨凌示范区
        else
        {
        	String c = code.substring(0, 4);
            switch (c)
            {
                case "6100": return "U";//省直管
                case "6101": return "A";//西安市
                case "6102": return "B";//铜川市
                case "6103": return "C";//宝鸡市
                case "6104": return "D";//咸阳市
                case "6105": return "E";//渭南市
                case "6106": return "J";//延安市
                case "6107": return "F";//汉中市
                case "6108": return "K";//榆林市
                case "6109": return "G";//安康市
                case "6110": return "H";//商洛市
                default: return "";
            }
        }
    }
	/**
	 * 系统级别
	 * @return
	 */
	public int SystemOrganLevel() {
		return this.System_Organ_Level;
	}
	private String UserId;//人员id
	private String Code;//服务中心编码
	private int OrganLevel;//服务中心级别 0国家级 1省级 2市级 3区级
	private String QueryCode;//查询编码
	private String SMSCode;//特别验证码
	private String ServerDomain;//服务器域名地址
	private String ServerScheme;//服务器域名地址
	
	
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public int getOrganLevel() {
		return OrganLevel;
	}
	public void setOrganLevel(int organLevel) {
		OrganLevel = organLevel;
	}
	public String getQueryCode() {
		return QueryCode;
	}
	public void setQueryCode(String queryCode) {
		QueryCode = queryCode;
	}
	public String getSMSCode() {
		return SMSCode;
	}
	public void setSMSCode(String sMSCode) {
		SMSCode = sMSCode;
	}
	public String getServerDomain() {
		return ServerDomain;
	}
	public void setServerDomain(String serverDomain) {
		ServerDomain = serverDomain;
	}
	public String getServerScheme() {
		return ServerScheme;
	}
	public void setServerScheme(String serverScheme) {
		ServerScheme = serverScheme;
	}
	
	protected void Initialize() {
		this.setServerDomain(req.getServerName());
		this.setServerScheme(req.getScheme());
		this.setUserId(getCookieValue(usercookie));
		this.setCode(getCookieValue(codecookie));
		if(this.Code==null || this.Code.equals("")) this.setCode("000000");
		this.setSMSCode(this.smscode);
		String prov = this.Code.substring(0, 2);
		String city = this.Code.substring(2, 4);
		String zone = this.Code.substring(4, 6);
        if (prov.equals("00")) this.OrganLevel = 0;
        else
        {
            if (city.equals("00")) this.OrganLevel = 1;
            else
            {
                if (zone.equals("00")) {
                	this.OrganLevel = 2;
                } 
                else this.OrganLevel = 3;
            }
        }
        this.QueryCode = GetQueryCode();
	}
	private String GetQueryCode()
    {
        if (this.Code==null || this.Code.equals("")) return "00";
        String prov = this.Code.substring(0, 2);
        String city = this.Code.substring(2, 4);
        String zone = this.Code.substring(4, 6);
        if (!zone.equals("00")) return prov + city + zone;
        if (!city.equals("00")) return prov + city;
        if (!prov.equals("00")) return prov;
        return "00";

    }
	
	private boolean IsAdd;//模块id +01
	private boolean IsEdit;//模块id +02
	private boolean IsDelete;//模块id +03
	private boolean IsEditAcount;//模块id +04
	private boolean IsImport;//导入 模块id+05
	private boolean IsExport;//导出 模块id+06
	private boolean IsAudit;//审核 模块id+07
	private boolean IsInvalid;//作废 模块id+08
	private boolean IsReview;//送审 模块id+09
	private boolean IsPrint;//打印 模块id+10
	private boolean IsChanged;//信息变更 模块id+11
	private boolean IsPintAccep;//打印业务受理单 模块id+21
	private boolean IsPintFile;//打印档案证 模块id+22
	private boolean IsPrintRegis;//打印登记证 模块id+23
	
	
	public boolean getIsAdd() {
		return IsAdd;
	}
	public void setIsAdd(boolean isAdd) {
		IsAdd = isAdd;
	}
	public boolean getIsEdit() {
		return IsEdit;
	}
	public void setIsEdit(boolean isEdit) {
		IsEdit = isEdit;
	}
	public boolean getIsDelete() {
		return IsDelete;
	}
	public void setIsDelete(boolean isDelete) {
		IsDelete = isDelete;
	}
	public boolean getIsEditAcount() {
		return IsEditAcount;
	}
	public void setIsEditAcount(boolean isEditAcount) {
		IsEditAcount = isEditAcount;
	}
	public boolean getIsImport() {
		return IsImport;
	}
	public void setIsImport(boolean isImport) {
		IsImport = isImport;
	}
	public boolean getIsExport() {
		return IsExport;
	}
	public void setIsExport(boolean isExport) {
		IsExport = isExport;
	}
	public boolean getIsAudit() {
		return IsAudit;
	}
	public void setIsAudit(boolean isAudit) {
		IsAudit = isAudit;
	}
	public boolean getIsInvalid() {
		return IsInvalid;
	}
	public void setIsInvalid(boolean isInvalid) {
		IsInvalid = isInvalid;
	}
	public boolean getIsReview() {
		return IsReview;
	}
	public void setIsReview(boolean isReview) {
		IsReview = isReview;
	}
	public boolean getIsPrint() {
		return IsPrint;
	}
	public void setIsPrint(boolean isPrint) {
		IsPrint = isPrint;
	}
	public boolean getIsChanged() {
		return IsChanged;
	}
	public void setIsChanged(boolean isChanged) {
		IsChanged = isChanged;
	}
	public boolean getIsPintAccep() {
		return IsPintAccep;
	}
	public void setIsPintAccep(boolean isPintAccep) {
		IsPintAccep = isPintAccep;
	}
	public boolean getIsPintFile() {
		return IsPintFile;
	}
	public void setIsPintFile(boolean isPintFile) {
		IsPintFile = isPintFile;
	}
	public boolean getIsPrintRegis() {
		return IsPrintRegis;
	}
	public void setIsPrintRegis(boolean isPrintRegis) {
		IsPrintRegis = isPrintRegis;
	}
	
	/**
	 * 加载用户权限
	 * @param module 模块id
	 */
	protected void OverloadMemberLimit(String module) {
		UserBean member = userRepository.selectRow(" id='"+this.getUserId()+"' limit 1");
		if(member==null) member = new UserBean();
		RoleBean role = roleRepository.selectRow(" id='"+member.getRoleid()+"' limit 1");
		if(role==null) role = new RoleBean();
		if(role.getLimits()==null) role.setLimits("");
		ArrayList<String> arrString = new ArrayList<>();
		String [] str = role.getLimits().split(",");
		for (String item : str) {
			arrString.add(item);
		}
        this.setIsAdd(member.getIsmanager()==1?true:(arrString.contains(module+"01")));
        this.setIsEdit(member.getIsmanager()==1?true:(arrString.contains(module+"02")));
        this.setIsDelete(member.getIsmanager()==1?true:(arrString.contains(module+"03")));
        this.setIsEditAcount(member.getIsmanager()==1?true:(arrString.contains(module+"04")));
        this.setIsImport(member.getIsmanager()==1?true:(arrString.contains(module+"05")));
        this.setIsExport(member.getIsmanager()==1?true:(arrString.contains(module+"06")));
        this.setIsAudit(member.getIsmanager()==1?true:(arrString.contains(module+"07")));
        this.setIsInvalid(member.getIsmanager()==1?true:(arrString.contains(module+"08")));
        this.setIsReview(member.getIsmanager()==1?true:(arrString.contains(module+"09")));
        this.setIsPrint(member.getIsmanager()==1?true:(arrString.contains(module+"10")));
        this.setIsChanged(member.getIsmanager()==1?true:(arrString.contains(module+"11")));
        this.setIsPintAccep(member.getIsmanager()==1?true:(arrString.contains(module+"21")));
        this.setIsPintFile(member.getIsmanager()==1?true:(arrString.contains(module+"22")));
        this.setIsPrintRegis(member.getIsmanager()==1?true:(arrString.contains(module+"23")));
	}
	
	protected AjaxResult onFailed(String msg) {
		return new AjaxResult(400, null, msg);
	}
	
	protected AjaxResult onSuccess(String msg) {
		return new AjaxResult(200, null, msg);
	}
	
	protected AjaxResult onSuccess(Object content,String msg) {
		return new AjaxResult(200, content, msg);
	}
}
