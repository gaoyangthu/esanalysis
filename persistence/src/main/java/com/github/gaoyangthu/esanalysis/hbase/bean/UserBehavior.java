package com.github.gaoyangthu.esanalysis.hbase.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-18
 * Time: 下午3:11
 */
public class UserBehavior implements Serializable {

	private String bdUserUuidTimestamp;

	private String bdUserUuid;

	private String url;

	private Integer urlRegexId;

	private String urlRegex;

	private Integer userBehaviorId;

	private String userBehaviorName;

	private String ip;

	private Date time;

	private int code;

	private String userAgent;

	public UserBehavior() {
	}

	public UserBehavior(String bdUserUuidTimestamp) {
		this.bdUserUuidTimestamp = bdUserUuidTimestamp;
	}

	public UserBehavior(String bdUserUuidTimestamp, String bdUserUuid,
			String url, Integer urlRegexId, String urlRegex,
			Integer userBehaviorId, String userBehaviorName, String ip,
			Date time, int code, String userAgent) {
		this.bdUserUuidTimestamp = bdUserUuidTimestamp;
		this.bdUserUuid = bdUserUuid;
		this.url = url;
		this.urlRegexId = urlRegexId;
		this.urlRegex = urlRegex;
		this.userBehaviorId = userBehaviorId;
		this.userBehaviorName = userBehaviorName;
		this.ip = ip;
		this.time = time;
		this.code = code;
		this.userAgent = userAgent;
	}

	public String getBdUserUuidTimestamp() {
		return bdUserUuidTimestamp;
	}

	public void setBdUserUuidTimestamp(String bdUserUuidTimestamp) {
		this.bdUserUuidTimestamp = bdUserUuidTimestamp;
	}

	public String getBdUserUuid() {
		return bdUserUuid;
	}

	public void setBdUserUuid(String bdUserUuid) {
		this.bdUserUuid = bdUserUuid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getUrlRegexId() {
		return urlRegexId;
	}

	public void setUrlRegexId(Integer urlRegexId) {
		this.urlRegexId = urlRegexId;
	}

	public String getUrlRegex() {
		return urlRegex;
	}

	public void setUrlRegex(String urlRegex) {
		this.urlRegex = urlRegex;
	}

	public Integer getUserBehaviorId() {
		return userBehaviorId;
	}

	public void setUserBehaviorId(Integer userBehaviorId) {
		this.userBehaviorId = userBehaviorId;
	}

	public String getUserBehaviorName() {
		return userBehaviorName;
	}

	public void setUserBehaviorName(String userBehaviorName) {
		this.userBehaviorName = userBehaviorName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("UserBehavior{");
		sb.append("bdUserUuidTimestamp='").append(bdUserUuidTimestamp).append(
				'\'');
		sb.append(", bdUserUuid='").append(bdUserUuid).append('\'');
		sb.append(", url='").append(url).append('\'');
		sb.append(", urlRegexId='").append(urlRegexId).append('\'');
		sb.append(", urlRegex='").append(urlRegex).append('\'');
		sb.append(", userBehaviorId='").append(userBehaviorId).append('\'');
		sb.append(", userBehaviorName='").append(userBehaviorName).append('\'');
		sb.append(", ip='").append(ip).append('\'');
		sb.append(", time=").append(time);
		sb.append(", code=").append(code);
		sb.append(", userAgent='").append(userAgent).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
