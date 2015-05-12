package com.github.gaoyangthu.esanalysis.bdb.dao;

import com.github.gaoyangthu.core.bdb.AbstractBerkeleyDao;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 2014/3/28
 * Time: 13:44
 */
public class BDBUserIndexReadDao extends AbstractBerkeleyDao<String> {

	static {
		path = "bdb";
		dbName = "user_index";
		isReadOnly = true;
	}

	private static volatile BDBUserIndexReadDao bdbUserIndexDao;

	private BDBUserIndexReadDao() {
		persistentClass = String.class;
	}

	public static BDBUserIndexReadDao getInstance() {
		if(bdbUserIndexDao == null) {
			synchronized (BDBUserIndexReadDao.class) {
				if(bdbUserIndexDao == null) {
					bdbUserIndexDao = new BDBUserIndexReadDao();
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
