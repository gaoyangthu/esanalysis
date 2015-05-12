package com.github.gaoyangthu.esanalysis.bdb.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 2014/3/28
 * Time: 15:29
 */
public class BDBUserChannelTag implements Serializable {

	private Integer channelId;
	private String channelCode;
	private String channelName;
	private Date channelTime;

	public BDBUserChannelTag() {
	}

	public BDBUserChannelTag(Integer channelId, String channelCode, String channelName, Date channelTime) {
		this.channelId = channelId;
		this.channelCode = channelCode;
		this.channelName = channelName;
		this.channelTime = channelTime;
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BDBUserChannelTag that = (BDBUserChannelTag) o;

		if (channelCode != null ? !channelCode.equals(that.channelCode) : that.channelCode != null) return false;
		if (channelId != null ? !channelId.equals(that.channelId) : that.channelId != null) return false;
		if (channelName != null ? !channelName.equals(that.channelName) : that.channelName != null) return false;
		if (channelTime != null ? !channelTime.equals(that.channelTime) : that.channelTime != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = channelId != null ? channelId.hashCode() : 0;
		result = 31 * result + (channelCode != null ? channelCode.hashCode() : 0);
		result = 31 * result + (channelName != null ? channelName.hashCode() : 0);
		result = 31 * result + (channelTime != null ? channelTime.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("BDBUserChannelTag{");
		sb.append("channelId=").append(channelId);
		sb.append(", channelCode='").append(channelCode).append('\'');
		sb.append(", channelName='").append(channelName).append('\'');
		sb.append(", channelTime=").append(channelTime);
		sb.append('}');
		return sb.toString();
	}
}
