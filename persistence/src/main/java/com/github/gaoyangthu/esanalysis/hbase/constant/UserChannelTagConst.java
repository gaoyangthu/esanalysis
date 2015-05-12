package com.github.gaoyangthu.esanalysis.hbase.constant;

import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-18
 * Time: 上午10:09
 */
public class UserChannelTagConst {

	public static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");// 等价于now.toLocaleString()

	/**
	 * 表名 user_channel_tag
	 */
	public static final String USER_CHANNEL_TAG = "user_channel_tag";

	/**
	 * 行键 bd_user_uuid_timestamp
	 */
	public static final String BD_USER_UUID_TIMESTAMP = "bd_user_uuid_timestamp";

	/**
	 * 列族 channel_tag
	 */
	public static final String CHANNEL_TAG = "channel_tag";

	/**
	 * 列 channel_tag:bd_user_uuid
	 */
	public static final String BD_USER_UUID = "bd_user_uuid";

	/**
	 * 列 channel_tag:channel_id
	 */
	public static final String CHANNEL_ID = "channel_id";

	/**
	 * 列 channel_tag:channel_code
	 */
	public static final String CHANNEL_CODE = "channel_code";

	/**
	 * 列 channel_tag:channel_name
	 */
	public static final String CHANNEL_NAME = "channel_name";

	/**
	 * 列 channel_tag:channel_time
	 */
	public static final String CHANNEL_TIME = "channel_time";
}
