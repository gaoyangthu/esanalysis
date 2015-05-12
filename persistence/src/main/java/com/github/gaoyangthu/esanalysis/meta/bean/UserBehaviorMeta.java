package com.github.gaoyangthu.esanalysis.meta.bean;

import java.util.Date;

public class UserBehaviorMeta {
	private Integer serialId;

	private String rowId;

	private String metaName;

	private String remarks;

	private String editLog;

	private Date updateTime;

	private Integer status;

	public Integer getSerialId() {
		return serialId;
	}

	public void setSerialId(Integer serialId) {
		this.serialId = serialId;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId == null ? null : rowId.trim();
	}

	public String getMetaName() {
		return metaName;
	}

	public void setMetaName(String metaName) {
		this.metaName = metaName == null ? null : metaName.trim();
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
	}

	public String getEditLog() {
		return editLog;
	}

	public void setEditLog(String editLog) {
		this.editLog = editLog == null ? null : editLog.trim();
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}