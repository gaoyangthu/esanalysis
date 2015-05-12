package com.github.gaoyangthu.esanalysis.hbase.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-13
 * Time: 上午11:17
 */
public class UserId implements Serializable {

	private String bdUserUuid;

	private Map<String, String> ids = new HashMap<String, String>();

	public UserId() {
	}

	public UserId(String bdUserUuid) {
		this.bdUserUuid = bdUserUuid;
	}

	public UserId(String bdUserUuid, Map<String, String> ids) {
		this.bdUserUuid = bdUserUuid;
		this.ids = ids;
	}

	public String getBdUserUuid() {
		return bdUserUuid;
	}

	public void setBdUserUuid(String bdUserUuid) {
		this.bdUserUuid = bdUserUuid;
	}

	public Map<String, String> getIds() {
		return ids;
	}

	public void setIds(Map<String, String> ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("UserId{");
		sb.append("bdUserUuid='").append(bdUserUuid).append('\'');
		sb.append(", ids=").append(ids);
		sb.append('}');
		return sb.toString();
	}
}
