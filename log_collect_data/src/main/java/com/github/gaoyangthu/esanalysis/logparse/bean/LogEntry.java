package com.github.gaoyangthu.esanalysis.logparse.bean;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-19
 * Time: 上午11:45
 * 
 * TODO 性能考虑，bean 单例线程内模式
 * CV_ID:X001
 */
public class LogEntry {

	String ip;
	String identity;
	String userId;
	String time;
	String url;
	String status;
	String length;
	String referrer;
	String userAgent;
	String cookieId;
	String accountId;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getCookieId() {
		return cookieId;
	}

	public void setCookieId(String cookieId) {
		this.cookieId = cookieId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("LogEntry{");
		sb.append("ip='").append(ip).append('\'');
		sb.append(", identity='").append(identity).append('\'');
		sb.append(", userId='").append(userId).append('\'');
		sb.append(", time='").append(time).append('\'');
		sb.append(", url='").append(url).append('\'');
		sb.append(", status='").append(status).append('\'');
		sb.append(", length='").append(length).append('\'');
		sb.append(", referrer='").append(referrer).append('\'');
		sb.append(", userAgent='").append(userAgent).append('\'');
		sb.append(", cookieId='").append(cookieId).append('\'');
		sb.append(", accountId='").append(accountId).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
