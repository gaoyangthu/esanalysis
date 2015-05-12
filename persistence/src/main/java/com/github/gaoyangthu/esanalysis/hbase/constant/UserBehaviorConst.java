package com.github.gaoyangthu.esanalysis.hbase.constant;

import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-18
 * Time: 下午3:11
 */
public class UserBehaviorConst {

	public static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");// 等价于now.toLocaleString()

	/**
	 * 表名 user_behavior
	 */
	public static final String USER_BEHAVIOR = "user_behavior";

	/**
	 * 行键 bd_user_uuid_timestamp
	 */
	public static final String BD_USER_UUID_TIMESTAMP = "bd_user_uuid_timestamp";

	/**
	 * 列族 behavior
	 */
	public static final String BEHAVIOR = "behavior";

	/**
	 * 列 behavior:bd_user_uuid
	 */
	public static final String BD_USER_UUID = "bd_user_uuid";

	/**
	 * 列 behavior:url
	 */
	public static final String URL = "url";

	/**
	 * 列 behavior:url_regex_id
	 */
	public static final String URL_REGEX_ID = "url_regex_id";

	/**
	 * 列 behavior:url_regex
	 */
	public static final String URL_REGEX = "url_regex";

	/**
	 * 列 behavior:user_behavior_id
	 */
	public static final String USER_BEHAVIOR_ID = "user_behavior_id";

	/**
	 * 列 behavior:user_behavior_name
	 */
	public static final String USER_BEHAVIOR_NAME = "user_behavior_name";

	/**
	 * 列 behavior:ip
	 */
	public static final String IP = "ip";

	/**
	 * 列 behavior:time
	 */
	public static final String TIME = "time";

	/**
	 * 列 behavior:code
	 */
	public static final String CODE = "code";

	/**
	 * 列 behavior:user_agent
	 */
	public static final String USER_AGENT = "user_agent";
}
