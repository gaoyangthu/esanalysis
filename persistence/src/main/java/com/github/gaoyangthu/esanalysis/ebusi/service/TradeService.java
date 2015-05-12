package com.github.gaoyangthu.esanalysis.ebusi.service;

import com.github.gaoyangthu.esanalysis.ebusi.bean.Trade;

import java.util.Date;
import java.util.List;

/**
 * trade表接口
 *
 * Author: gaoyangthu
 * Date: 14-3-12
 * Time: 下午5:41
 */
public interface TradeService {

	/**
	 * 根据主订单ID查询交易
	 *
	 * @param sourceId 主订单ID
	 * @return
	 */
	Trade findOrderTrade(String sourceId);

	/**
	 * 根据时间查询交易
	 *
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return 交易列表
	 */
	List<Trade> findByDate(Date beginDate, Date endDate);

}
