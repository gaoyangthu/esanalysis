package com.github.gaoyangthu.esanalysis.meta.bean;

import java.util.Date;

public class LogParseRule {
	private Integer serialId;

	private Object fatherIds;

	private Object childIds;

	private Integer finishTag;

	private String remarks;

	private String editLog;

	private Date updateTime;

	private Integer status;

	private String ruleName;

	private String parseRegex;

	private Object groupDict;

	public Integer getSerialId() {
		return serialId;
	}

	public void setSerialId(Integer serialId) {
		this.serialId = serialId;
	}

	public Object getFatherIds() {
		return fatherIds;
	}

	public void setFatherIds(Object fatherIds) {
		this.fatherIds = fatherIds;
	}

	public Object getChildIds() {
		return childIds;
	}

	public void setChildIds(Object childIds) {
		this.childIds = childIds;
	}

	public Integer getFinishTag() {
		return finishTag;
	}

	public void setFinishTag(Integer finishTag) {
		this.finishTag = finishTag;
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

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName == null ? null : ruleName.trim();
	}

	public String getParseRegex() {
		return parseRegex;
	}

	public void setParseRegex(String parseRegex) {
		this.parseRegex = parseRegex == null ? null : parseRegex.trim();
	}

	public Object getGroupDict() {
		return groupDict;
	}

	public void setGroupDict(Object groupDict) {
		this.groupDict = groupDict;
	}
}