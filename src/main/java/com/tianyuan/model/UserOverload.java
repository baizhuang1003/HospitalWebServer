package com.tianyuan.model;

import com.tianyuan.controller.BaseManageController;

public class UserOverload {
	
	public UserOverload() {
		
	}
	
	public UserOverload(BaseManageController controller) {
		this.setIsAdd(controller.getIsAdd());
		this.setIsEdit(controller.getIsEdit());
		this.setIsDelete(controller.getIsDelete());
		this.setIsEditAcount(controller.getIsEditAcount());
		this.setIsImport(controller.getIsImport());
		this.setIsExport(controller.getIsExport());
		this.setIsAudit(controller.getIsAudit());
		this.setIsReview(controller.getIsReview());
		this.setIsPrint(controller.getIsPrint());
		this.setIsInvalid(controller.getIsInvalid());
		this.setIsPintAccep(controller.getIsPintAccep());
		this.setIsPintFile(controller.getIsPintFile());
		this.setIsPrintRegis(controller.getIsPrintRegis());
		this.setOrganLevel(controller.getOrganLevel());
		this.setOrganCode(controller.getCode());
		this.setIsChanged(controller.getIsChanged());
		this.setSystemOrganLevel(controller.SystemOrganLevel());
	}
	
	private int SystemOrganLevel;//系统级别

	private int OrganLevel;//级别

	private String OrganCode;//区域代码

	private boolean IsAdd; //模块id +01
	private boolean IsEdit; //模块id +02
	private boolean IsDelete; //模块id +03
	private boolean IsEditAcount; //模块id +04

	private boolean IsImport; //导入 模块id+05
	private boolean IsExport;//导出 模块id+06


	private boolean IsAudit;//审核 模块id+07
	private boolean IsInvalid;//作废 模块id+08
	private boolean IsReview;//送审 模块id+09
	private boolean IsPrint;//打印 模块id+10

	private boolean IsChanged;//变更 模块id+11

	private boolean IsPintAccep;//打印业务受理单 模块id+21
	private boolean IsPintFile;//打印档案证 模块id+22
	private boolean IsPrintRegis;//打印登记证 模块id+23
	public int getSystemOrganLevel() {
		return SystemOrganLevel;
	}

	public void setSystemOrganLevel(int systemOrganLevel) {
		SystemOrganLevel = systemOrganLevel;
	}

	public int getOrganLevel() {
		return OrganLevel;
	}

	public void setOrganLevel(int organLevel) {
		OrganLevel = organLevel;
	}

	public String getOrganCode() {
		return OrganCode;
	}

	public void setOrganCode(String organCode) {
		OrganCode = organCode;
	}

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
	
}
