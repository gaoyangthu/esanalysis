package com.github.gaoyangthu.esanalysis.hbase.service;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-13
 * Time: 上午9:50
 */
@Deprecated
public interface UserTagService {

	/**
	 * 添加渠道标签
	 *
	 * @param bdUserUuid
	 * @param channelId
	 * @param channelCode
	 * @param channelName
	 * @param channleTime
	 * @return
	 */
	boolean addChannelTag(String bdUserUuid, String channelId,
			String channelCode, String channelName, Date channleTime);

	/**
	 * 添加交易标签
	 *
	 * @param bdUserUuid
	 * @param tradeId
	 * @param tradeName
	 * @param tradeAmount
	 * @param channelTime
	 * @return
	 */
	boolean addTradeTag(String bdUserUuid, String tradeId, String tradeName,
			String tradeAmount, Date channelTime);
}
