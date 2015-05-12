package com.github.gaoyangthu.esanalysis.hbase.service;

import com.github.gaoyangthu.esanalysis.hbase.bean.UserId;
import com.github.gaoyangthu.esanalysis.hbase.bean.UserIndex;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-13
 * Time: 上午9:50
 */
public interface UserIdService {

	/**
	 * 查询所有用户id
	 *	
	 * TODO 遍历全量数据，请使用  Iterator设计模式,以防内存溢出
	 * @return
	 */
	List<UserId> findAllUserId();

	//TODO 遍历全量数据，请使用  Iterator设计模式,以防内存溢出
	//TODO 抽象出一层 : super.findALL( 线程数|默认1线程 ) 让findAll都可以多线程 scan > m-queue > iterator  (这里锁的使用，需要非常小心 等展开这个功能能时 细聊)   
	List<UserIndex> findAllUserIndex();

	/**
	 * 根据id获取用户的bdUserUuid
	 *
	 * @param uaId
	 * @param cookieId
	 * @param accountId
	 * @return
	 */
	String getBdUserUuid(String uaId, String cookieId, String accountId);

}
