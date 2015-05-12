package com.github.gaoyangthu.esanalysis.meta.dao;

import com.github.gaoyangthu.esanalysis.meta.bean.ChannelRule;

import java.util.List;

public interface ChannelRuleMapper {
	int deleteByPrimaryKey(Integer serialId);

	int insert(ChannelRule record);

	int insertSelective(ChannelRule record);

	ChannelRule selectByPrimaryKey(Integer serialId);

	int updateByPrimaryKeySelective(ChannelRule record);

	int updateByPrimaryKey(ChannelRule record);

	ChannelRule findByChannelCode(String channelCode);

	ChannelRule findByChannelName(String channelName);

	List<ChannelRule> findAll();
}