package com.github.gaoyangthu.esanalysis.bdb.dao;

import com.github.gaoyangthu.core.bdb.AbstractBerkeleyDao;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 2014/3/28
 * Time: 14:05
 */
public class BDBUserChannelTagDao extends AbstractBerkeleyDao<Set> {

	static {
		path = "bdb";
		dbName = "user_channel_tag";
		isReadOnly = false;
	}

	private static volatile BDBUserChannelTagDao bdbUserChannelTagDao;

	private BDBUserChannelTagDao() {
		persistentClass = Set.class;
	}

	public static BDBUserChannelTagDao getInstance() {
		if(bdbUserChannelTagDao == null) {
			synchronized (BDBUserIndexDao.class) {
				if(bdbUserChannelTagDao == null) {
					bdbUserChannelTagDao = new BDBUserChannelTagDao();
				}
			}
		}
		return bdbUserChannelTagDao;
	}

	public static void close() {
		if(bdbUserChannelTagDao != null) {
			bdbUserChannelTagDao.closeConnection();
		}
	}

}
