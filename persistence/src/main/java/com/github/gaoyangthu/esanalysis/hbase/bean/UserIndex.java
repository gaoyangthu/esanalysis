package com.github.gaoyangthu.esanalysis.hbase.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-13
 * Time: 下午7:09
 */
public class UserIndex implements Serializable {

	private String id;

	private String bdUserUuid;

	private Long timestamp;

	public UserIndex() {
	}

	public UserIndex(String id, String bdUserUuid) {
		this.id = id;
		this.bdUserUuid = bdUserUuid;
	}

	public UserIndex(String id, String bdUserUuid, Long timestamp) {
		this.id = id;
		this.bdUserUuid = bdUserUuid;
		this.timestamp = timestamp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBdUserUuid() {
		return bdUserUuid;
	}

	public void setBdUserUuid(String bdUserUuid) {
		this.bdUserUuid = bdUserUuid;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("UserIndex{");
		sb.append("id='").append(id).append('\'');
		sb.append(", bdUserUuid='").append(bdUserUuid).append('\'');
		sb.append(", timestamp=").append(timestamp);
		sb.append('}');
		return sb.toString();
	}
}
