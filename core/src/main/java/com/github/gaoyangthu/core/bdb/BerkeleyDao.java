package com.github.gaoyangthu.core.bdb;

/**
 * Berkeley DB的通用接口
 *
 * Author: gaoyangthu
 * Date: 2014/3/28
 * Time: 10:51
 */
public interface BerkeleyDao<T> {

	/**
	 * 打开数据库连接
	 *
	 * @param path 文件路径
	 * @param dbName 数据库名
	 * @return 是否成功
	 */
	boolean openConnection(String path, String dbName);

	/**
	 * 关闭数据库连接
	 *
	 * @return 是否成功
	 */
	boolean closeConnection();

	/**
	 * 插入
	 *
	 * @param key 键
	 * @param value 值
	 * @return 插入的值
	 */
	T create(String key, T value);

	/**
	 * 删除
	 *
	 * @param key 键
	 * @return 删除的值
	 */
	T delete(String key);

	/**
	 * 更新
	 *
	 * @param key 键
	 * @param value 值
	 * @return 更新的值
	 */
	T update(String key, T value);

	/**
	 * 创建或更新
	 *
	 * @param key 键
	 * @param value 值
	 * @return 创建或更新的值
	 */
	T createOrUpdate(String key, T value);

	/**
	 * 查找
	 *
	 * @param key 键
	 * @return 值
	 */
	T get(String key);
}
