package com.github.gaoyangthu.esanalysis.ebusi.bean;

import java.util.Date;

public class Trade {
	private String tradeId;

	private Integer tradeType;

	private String fkSourceId;

	private String fkAccountId;

	private String fkUserId;

	private Date tradeTime;

	private Integer status;

	private String extraInfo;

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId == null ? null : tradeId.trim();
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public String getFkSourceId() {
		return fkSourceId;
	}

	public void setFkSourceId(String fkSourceId) {
		this.fkSourceId = fkSourceId == null ? null : fkSourceId.trim();
	}

	public String getFkAccountId() {
		return fkAccountId;
	}

	public void setFkAccountId(String fkAccountId) {
		this.fkAccountId = fkAccountId == null ? null : fkAccountId.trim();
	}

	public String getFkUserId() {
		return fkUserId;
	}

	public void setFkUserId(String fkUserId) {
		this.fkUserId = fkUserId == null ? null : fkUserId.trim();
	}

	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo == null ? null : extraInfo.trim();
	}
}