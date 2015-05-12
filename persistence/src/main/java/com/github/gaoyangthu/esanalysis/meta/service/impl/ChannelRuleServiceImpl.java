package com.github.gaoyangthu.esanalysis.meta.service.impl;

import com.github.gaoyangthu.core.postgresql.PostgresUtil;
import com.github.gaoyangthu.esanalysis.meta.bean.ChannelRule;
import com.github.gaoyangthu.esanalysis.meta.dao.ChannelRuleMapper;
import com.github.gaoyangthu.esanalysis.meta.service.ChannelRuleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-19
 * Time: 下午4:13
 */
public class ChannelRuleServiceImpl implements ChannelRuleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChannelRuleServiceImpl.class);

	private static AtomicBoolean isInited = new AtomicBoolean(false);
	/**
	 * 内部缓存, key:channelCode, value:ChannelRule
	 */
	private static volatile Map<String, ChannelRule> channelRuleMap = new ConcurrentHashMap<String, ChannelRule>();

	public ChannelRuleServiceImpl() {
		if(!isInited.get()) {
			init();
			isInited.compareAndSet(false, true);
		}
	}

	private static void init() {
		SqlSession sqlSession = PostgresUtil.getSessionFactory().openSession();
		try {
			ChannelRuleMapper channelRuleMapper = sqlSession.getMapper(ChannelRuleMapper.class);
			List<ChannelRule> channelRules = channelRuleMapper.findAll();
			Lock lock = new ReentrantLock();
			try {
				lock.lock();
				channelRuleMap.clear();
				for (ChannelRule channelRule : channelRules) {
					if ((channelRule != null)
							&& (StringUtils.isNotBlank(channelRule.getChannelCode()))) {
						channelRuleMap.put(channelRule.getChannelCode(),
								channelRule);
					}
				}
			} finally {
				lock.unlock();
			}
		} finally {
			sqlSession.close();
		}
	}

	public static void reload() {
		init();
		isInited.set(true);
	}

	/**
	 * 根据渠道码查询渠道规则
	 *
	 * @param channelCode 渠道码
	 * @return 渠道码对应的渠道规则
	 */
	@Override
	public ChannelRule findByChannelCode(String channelCode) {
		ChannelRule channelRule = channelRuleMap.get(channelCode);
		return channelRule;
	}

	/**
	 * 根据渠道名查询渠道规则
	 *
	 * @param channelName 渠道名
	 * @return 渠道名对应的渠道规则
	 */
	@Override
	public ChannelRule findByChannelName(String channelName) {
		return null;
	}

	/**
	 * 查询所有渠道
	 * @return 渠道列表
	 */
	@Override
	public List<ChannelRule> findAll() {
		List<ChannelRule> channelRules = new ArrayList<ChannelRule>(
				channelRuleMap.values());
		return channelRules;
	}
}
