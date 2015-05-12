package com.github.gaoyangthu.esanalysis.meta.service;

import com.github.gaoyangthu.esanalysis.meta.bean.ChannelRule;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author : gaoyangthu
 * Date: 14-3-19
 * Time: 下午3:52
 */
public interface ChannelRuleService {

	/**
	 * 根据渠道码查询渠道规则
	 *
	 * @param channelCode 渠道码
	 * @return 渠道码对应的渠道规则
	 */
	ChannelRule findByChannelCode(String channelCode);

	/**
	 * 根据渠道名查询渠道规则
	 *
	 * @param channelName 渠道名
	 * @return 渠道名对应的渠道规则
	 */
	ChannelRule findByChannelName(String channelName);

	/**
	 * 查询所有渠道
	 * @return 渠道列表
	 */
	List<ChannelRule> findAll();

}
