package com.github.gaoyangthu.esanalysis.ebusi.dao;

import com.github.gaoyangthu.esanalysis.ebusi.bean.Trade;

import java.util.List;
import java.util.Map;

public interface TradeMapper {
	int deleteByPrimaryKey(String tradeId);

	int insert(Trade record);

	int insertSelective(Trade record);

	Trade selectByPrimaryKey(String tradeId);

	int updateByPrimaryKeySelective(Trade record);

	int updateByPrimaryKeyWithBLOBs(Trade record);

	int updateByPrimaryKey(Trade record);

	Trade findOrderTrade(String sourceId);

	List<Trade> findByDate(Map<String, Object> params);
}