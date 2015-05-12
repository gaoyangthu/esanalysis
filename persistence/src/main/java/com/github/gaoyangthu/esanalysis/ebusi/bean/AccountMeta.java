package com.github.gaoyangthu.esanalysis.ebusi.bean;

import java.util.Date;

public class AccountMeta {
	private String accountId;

	private String loginName;

	private String loginEmail;

	private String accountNo;

	private String fkRootUserid;

	private Date createDate;

	private Integer accountType;

	private Integer status;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId == null ? null : accountId.trim();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName == null ? null : loginName.trim();
	}

	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail == null ? null : loginEmail.trim();
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo == null ? null : accountNo.trim();
	}

	public String getFkRootUserid() {
		return fkRootUserid;
	}

	public void setFkRootUserid(String fkRootUserid) {
		this.fkRootUserid = fkRootUserid == null ? null : fkRootUserid.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}