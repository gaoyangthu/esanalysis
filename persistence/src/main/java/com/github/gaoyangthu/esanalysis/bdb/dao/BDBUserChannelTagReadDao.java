package com.github.gaoyangthu.esanalysis.bdb.dao;

import java.util.Set;

import com.github.gaoyangthu.core.bdb.AbstractBerkeleyDao;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 2014/3/28
 * Time: 14:05
 */
public class BDBUserChannelTagReadDao extends AbstractBerkeleyDao<Set> {

	static {
		path = "bdb";
		dbName = "user_channel_tag";
		isReadOnly = true;
	}

	private static volatile BDBUserChannelTagReadDao bdbUserChannelTagDao;

	private BDBUserChannelTagReadDao() {
		persistentClass = Set.class;
	}

	public static BDBUserChannelTagReadDao getInstance() {
		if(bdbUserChannelTagDao == null) {
			synchronized (BDBUserIndexDao.class) {
				if(bdbUserChannelTagDao == null) {
					bdbUserChannelTagDao = new BDBUserChannelTagReadDao();
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
