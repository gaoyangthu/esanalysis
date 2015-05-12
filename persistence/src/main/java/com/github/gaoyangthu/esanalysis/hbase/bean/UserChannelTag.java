package com.github.gaoyangthu.esanalysis.hbase.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-18
 * Time: 上午10:15
 */
public class UserChannelTag implements Serializable {

	private String bdUserUuidTimestamp;

	private String bdUserUuid;
	private Integer channelId;
	private String channelCode;
	private String channelName;
	private Date channelTime;

	public UserChannelTag() {
	}

	public UserChannelTag(String bdUserUuidTimestamp) {
		this.bdUserUuidTimestamp = bdUserUuidTimestamp;
	}

	public UserChannelTag(String bdUserUuidTimestamp, String bdUserUuid,
			Integer channelId, String channelCode, String channelName,
			Date channelTime) {
		this.bdUserUuidTimestamp = bdUserUuidTimestamp;
		this.bdUserUuid = bdUserUuid;
		this.channelId = channelId;
		this.channelCode = channelCode;
		this.channelName = channelName;
		this.channelTime = channelTime;
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

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Date getChannelTime() {
		return channelTime;
	}

	public void setChannelTime(Date channelTime) {
		this.channelTime = channelTime;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("UserChannelTag{");
		sb.append("bdUserUuidTimestamp='").append(bdUserUuidTimestamp).append(
				'\'');
		sb.append(", bdUserUuid='").append(bdUserUuid).append('\'');
		sb.append(", channelId='").append(channelId).append('\'');
		sb.append(", channelCode='").append(channelCode).append('\'');
		sb.append(", channelName='").append(channelName).append('\'');
		sb.append(", channelTime=").append(channelTime);
		sb.append('}');
		return sb.toString();
	}
}
