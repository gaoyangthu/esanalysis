package com.github.gaoyangthu.core.bdb;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.je.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Berkeley DB 抽象类
 *
 * Author: gaoyangthu
 * Date: 2014/3/28
 * Time: 11:16
 */
public abstract class AbstractBerkeleyDao<T> implements BerkeleyDao<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBerkeleyDao.class);

	protected static String path;
	protected static String dbName;
	protected static boolean isReadOnly = false;

	protected Class<T> persistentClass = null;

	protected Environment env = null;
	protected Database database = null;
	protected StoredMap<String, T> storedMap = null;
	protected EntryBinding<String> keyBinding = null;
	protected SerialBinding<T> valueBinding = null;

	protected boolean isInit = false;

	/**
	 * 打开数据库连接
	 *
	 * @param path   文件路径
	 * @param dbName 数据库名
	 * @return 是否成功
	 */
	@Override
	public boolean openConnection(String path, String dbName) {
		boolean flag = true;

		if (!isInit) {
			try {
				File file = new File(path);
				EnvironmentConfig envConfig = new EnvironmentConfig();
				envConfig.setAllowCreate(true);
				envConfig.setTransactional(false);
				envConfig.setReadOnly(isReadOnly);
				env = new Environment(file, envConfig);

				DatabaseConfig databaseConfig = new DatabaseConfig();
				databaseConfig.setAllowCreate(true);
				databaseConfig.setTransactional(false);
				databaseConfig.setReadOnly(isReadOnly);
				database = env.openDatabase(null, dbName, databaseConfig);

				StoredClassCatalog catalog = new StoredClassCatalog(database);
				keyBinding = new SerialBinding<String>(catalog, String.class);
				valueBinding = new SerialBinding<T>(catalog, persistentClass);
				storedMap = new StoredMap<String, T>(database, keyBinding,
						valueBinding, true);

				isInit = true;

				flag = true;
			} catch (DatabaseException dbe) {
				LOGGER.error("Open BDB connection error.", dbe);
				flag = false;
			}
		}

		return flag;
	}

	/**
	 * 关闭数据库连接
	 *
	 * @return 是否成功
	 */
	@Override
	public boolean closeConnection() {
		boolean flag = false;
		try {
			if (database != null) {
				database.close();

			}
			if (env != null) {
				env.cleanLog();
				env.close();
			}
			flag = true;
		} catch (DatabaseException dbe) {
			LOGGER.error("Close BDB connection error.", dbe);
			flag = false;
		}
		return flag;
	}

	/**
	 * 插入
	 *
	 * @param key   键
	 * @param value 值
	 * @return 插入的值
	 */
	@Override
	public T create(String key, T value) {
		try {
			if ((storedMap == null) && !isInit) {
				openConnection(path, dbName);
			}
			if (storedMap != null) {
				return storedMap.put(key, value);
			} else {
				return null;
			}
		} catch (DatabaseException dbe) {
			LOGGER.error("Insert BDB error. key={}, value={}, error={}", key,
					value, dbe);
			return null;
		}
	}

	/**
	 * 删除
	 *
	 * @param key 键
	 * @return 删除的值
	 */
	@Override
	public T delete(String key) {
		try {
			if ((storedMap == null) && !isInit) {
				openConnection(path, dbName);
			}
			if (storedMap != null) {
				return storedMap.remove(key);
			} else {
				return null;
			}
		} catch (DatabaseException dbe) {
			LOGGER.error("Delete BDB error. key={}, error={}", key, dbe);
			return null;
		}
	}

	/**
	 * 更新
	 *
	 * @param key   键
	 * @param value 值
	 * @return 更新的值
	 */
	@Override
	public T update(String key, T value) {
		try {
			if ((storedMap == null) && !isInit) {
				openConnection(path, dbName);
			}
			if (storedMap != null) {
				return storedMap.put(key, value);
			} else {
				return null;
			}
		} catch (DatabaseException dbe) {
			LOGGER.error("Update BDB error. key={}, value={}, error={}", key,
					value, dbe);
			return null;
		}
	}

	/**
	 * 创建或更新
	 *
	 * @param key   键
	 * @param value 值
	 * @return 创建或更新的值
	 */
	@Override
	public T createOrUpdate(String key, T value) {
		try {
			if ((storedMap == null) && !isInit) {
				openConnection(path, dbName);
			}
			if (storedMap != null) {
				return storedMap.put(key, value);
			} else {
				return null;
			}
		} catch (DatabaseException dbe) {
			LOGGER.error(
					"CreateOrUpdate BDB error. key={}, value={}, error={}",
					key, value, dbe);
			return null;
		}
	}

	/**
	 * 查找
	 *
	 * @param key 键
	 * @return 值
	 */
	@Override
	public T get(String key) {
		try {
			if ((storedMap == null) && !isInit) {
				openConnection(path, dbName);
			}
			if (storedMap != null) {
				return storedMap.get(key);
			} else {
				return null;
			}
		} catch (DatabaseException dbe) {
			LOGGER.error("Get BDB error. key={}, error={}", key, dbe);
			return null;
		}
	}

	public static String getPath() {
		return path;
	}

	public static void setPath(String path) {
		AbstractBerkeleyDao.path = path;
	}

	public static String getDbName() {
		return dbName;
	}

	public static void setDbName(String dbName) {
		AbstractBerkeleyDao.dbName = dbName;
	}
}
