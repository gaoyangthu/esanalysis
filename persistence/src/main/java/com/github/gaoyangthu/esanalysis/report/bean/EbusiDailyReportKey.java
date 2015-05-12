package com.github.gaoyangthu.esanalysis.report.bean;

import java.util.Date;

public class EbusiDailyReportKey {
	private String bdUserUuid;

	private Date date;

	public String getBdUserUuid() {
		return bdUserUuid;
	}

	public void setBdUserUuid(String bdUserUuid) {
		this.bdUserUuid = bdUserUuid == null ? null : bdUserUuid.trim();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}