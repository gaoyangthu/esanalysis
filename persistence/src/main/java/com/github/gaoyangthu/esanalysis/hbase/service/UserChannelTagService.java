package com.github.gaoyangthu.esanalysis.hbase.service;

import com.github.gaoyangthu.esanalysis.hbase.bean.UserChannelTag;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-18
 * Time: 上午11:20
 */
public interface UserChannelTagService {

	/**
	 * 添加渠道标签
	 *
	 * @param bdUserUuid
	 * @param channelId
	 * @param channelCode
	 * @param channelName
	 * @param channelTime
	 * @return
	 */
	boolean addChannelTag(String bdUserUuid, Integer channelId,
			String channelCode, String channelName, Date channelTime);

	/**
	 * 查询所有用户渠道标签
	 *
	 * @return
	 */
	List<UserChannelTag> findAll();

	/**
	 * 根据用户的bdUserUuid查找渠道标签列表
	 *
	 * @param bdUserUuid
	 * @return
	 */
	List<UserChannelTag> findByBdUserId(String bdUserUuid);

	/**
	 * 根据渠道码查询用户渠道标签
	 *
	 * @param channelCode
	 * @return
	 */
	List<UserChannelTag> findByChannelCode(String channelCode);

	/**
	 * 根据渠道名称查询用户渠道标签
	 *
	 * @param channelName
	 * @return
	 */
	List<UserChannelTag> findByChannelName(String channelName);

	/**
	 * 根据值查询用户渠道标签
	 *
	 * @param family
	 * @param qualifier
	 * @param value
	 * @return
	 */
	List<UserChannelTag> findByValue(String family, String qualifier,
			String value);

	/**
	 * 更新用户ID
	 *
	 * @param oldId
	 * @param newId
	 * @return
	 */
	boolean changeBdUser(String oldId, String newId);
}
