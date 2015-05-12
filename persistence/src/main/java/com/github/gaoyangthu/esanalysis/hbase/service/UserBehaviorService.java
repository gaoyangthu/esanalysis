package com.github.gaoyangthu.esanalysis.hbase.service;

import com.github.gaoyangthu.esanalysis.hbase.bean.UserBehavior;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-18
 * Time: 下午4:00
 */
public interface UserBehaviorService {

	/**
	 * 添加用户行为
	 *
	 * @param bdUserUuid
	 * @param url
	 * @param urlRegexId
	 * @param urlRegex
	 * @param userBehaviorId
	 * @param userBehaviorName
	 * @param ip
	 * @param time
	 * @param code
	 * @param userAgent
	 * @return
	 */
	boolean addUserBehavior(String bdUserUuid, String url, Integer urlRegexId,
			String urlRegex, Integer userBehaviorId, String userBehaviorName,
			String ip, Date time, int code, String userAgent);

	/**
	 * 根据bdUserUuid查找用户行为
	 *
	 * @param bdUserUuid
	 * @return
	 */
	List<UserBehavior> findByBdUserId(String bdUserUuid);

	/**
	 * 根据urlRegexId查找用户行为
	 *
	 * @param urlRegexId
	 * @return
	 */
	List<UserBehavior> findByUrlRegexId(Integer urlRegexId);

	/**
	 * 根据urlRegex查找用户行为
	 *
	 * @param urlRegex
	 * @return
	 */
	List<UserBehavior> findByUrlRegex(String urlRegex);

	/**
	 * 根据userBehaviorId查找用户行为
	 *
	 * @param userBehaviorId
	 * @return
	 */
	List<UserBehavior> findByUserBehaviorId(Integer userBehaviorId);

	/**
	 * 根据userBehavior查找用户行为
	 *
	 * @param userBehavior
	 * @return
	 */
	List<UserBehavior> findByUserBehavior(String userBehavior);

	/**
	 * 根据值查找用户行为
	 *
	 * @param family
	 * @param qualifier
	 * @param value
	 * @return
	 */
	List<UserBehavior> findByValue(String family, String qualifier, String value);

	/**
	 * 更新用户ID
	 *
	 * @param oldId
	 * @param newId
	 * @return
	 */
	boolean  changeBdUser(String oldId, String newId);
}
