package com.github.gaoyangthu.esanalysis.bdb.dao;

import com.github.gaoyangthu.core.bdb.AbstractBerkeleyDao;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 2014/3/28
 * Time: 13:44
 */
public class BDBUserIndexDao extends AbstractBerkeleyDao<String> {

	static {
		path = "bdb";
		dbName = "user_index";
		isReadOnly = false;
	}

	private static volatile BDBUserIndexDao bdbUserIndexDao;

	private BDBUserIndexDao() {
		persistentClass = String.class;
	}

	public static BDBUserIndexDao getInstance() {
		if(bdbUserIndexDao == null) {
			synchronized (BDBUserIndexDao.class) {
				if(bdbUserIndexDao == null) {
					bdbUserIndexDao = new BDBUserIndexDao();
				}
			}
		}
		return bdbUserIndexDao;
	}

	public static void close() {
		if(bdbUserIndexDao != null) {
			bdbUserIndexDao.closeConnection();
		}
	}

}
