package com.github.gaoyangthu.esanalysis.ebusi.service.impl;

import com.github.gaoyangthu.core.mysql.EbusiUtil;
import com.github.gaoyangthu.esanalysis.ebusi.bean.Trade;
import com.github.gaoyangthu.esanalysis.ebusi.dao.TradeMapper;
import com.github.gaoyangthu.esanalysis.ebusi.service.TradeService;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * master_order表实现
 *
 * Author: gaoyangthu
 * Date: 14-3-12
 * Time: 下午5:42
 */
public class TradeServiceImpl implements TradeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

	/**
	 * 根据主订单ID查询交易
	 *
	 * @param sourceId 主订单ID
	 * @return
	 */
	@Override
	public Trade findOrderTrade(String sourceId) {
		Trade orderTrade = null;

		SqlSession sqlSession = EbusiUtil.getSessionFactory().openSession();
		try {
			TradeMapper tradeMapper = sqlSession.getMapper(TradeMapper.class);
			orderTrade = tradeMapper.findOrderTrade(sourceId);
		} finally {
			sqlSession.close();
		}

		return orderTrade;
	}

	/**
	 * 根据时间查询交易
	 *
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return 交易列表
	 */
	@Override
	public List<Trade> findByDate(Date beginDate, Date endDate) {
		List<Trade> trades = null;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);

		SqlSession sqlSession = EbusiUtil.getSessionFactory().openSession();
		try {
			TradeMapper tradeMapper = sqlSession.getMapper(TradeMapper.class);
			trades = tradeMapper.findByDate(params);
		} finally {
			sqlSession.close();
		}

		return trades;
	}
}
