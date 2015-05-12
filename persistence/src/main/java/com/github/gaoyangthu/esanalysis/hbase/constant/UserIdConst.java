package com.github.gaoyangthu.esanalysis.hbase.constant;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-13
 * Time: 下午6:33
 */
public class UserIdConst {

	/**
	 * 表名 user_id
	 */
	public static final String USER_ID = "user_id";

	/**
	 * 表名 user_index
	 */
	public static final String USER_INDEX = "user_index";

	/**
	 * bd_user_uuid
	 * 在user_id表里是行键
	 * 在user_index表里是列族
	 */
	public static final String BD_USER_UUID = "bd_user_uuid";

	/**
	 * id
	 * 在user_id表里是列族
	 * 在user_index表里是行键
	 */
	public static final String ID = "id";

	/**
	 * 列 id:channel_code
	 */
	public static final String UA_ID = "ua_id";

	/**
	 * 列 id:cookie_id
	 */
	public static final String COOKIE_ID = "cookie_id";

	/**
	 * 列 id:account_id
	 */
	public static final String ACCOUNT_ID = "account_id";
}
