package com.github.gaoyangthu.esanalysis.meta.service.impl;

import com.github.gaoyangthu.core.postgresql.PostgresUtil;
import com.github.gaoyangthu.esanalysis.meta.bean.UrlBehaviorRule;
import com.github.gaoyangthu.esanalysis.meta.dao.UrlBehaviorRuleMapper;
import com.github.gaoyangthu.esanalysis.meta.service.UrlBehaviorRuleService;
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
 * Time: 下午4:38
 */
public class UrlBehaviorRuleServiceImpl implements UrlBehaviorRuleService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChannelRuleServiceImpl.class);

	private static AtomicBoolean isInited = new AtomicBoolean(false);
	/**
	 * 内部缓存, key:urlRegex, value:UrlBehaviorRule
	 */
	private static volatile Map<String, UrlBehaviorRule> urlBehaviorRuleMap = new ConcurrentHashMap<String, UrlBehaviorRule>();

	//TODO isInited 请使用 atomic 类型的 boolean
	//TODO 构造函数调用   用静态的  volatile变量（出发点事为了性能和线程安全用内存空间换取，在构造的时候又使用 锁）;  直接在静态环境里 锁自己 给出缓冲内容可否？我也是yy
	// 没静态的目的,是为了用到时才生成数据,可节省启动时间和开始的内存
	public UrlBehaviorRuleServiceImpl() {
		if(!isInited.get()) {
			init();
			isInited.compareAndSet(false, true);
		}
	}

	private static void init() {
		SqlSession sqlSession = PostgresUtil.getSessionFactory().openSession();
		try {
			UrlBehaviorRuleMapper urlBehaviorRuleMapper = sqlSession.getMapper(UrlBehaviorRuleMapper.class);
			List<UrlBehaviorRule> urlBehaviorRules = urlBehaviorRuleMapper.findAll();
			Lock lock = new ReentrantLock();
			try {
				lock.lock();
				urlBehaviorRuleMap.clear();
				for (UrlBehaviorRule urlBehaviorRule : urlBehaviorRules) {
					if ((urlBehaviorRule != null)
							&& (StringUtils.isNotBlank(urlBehaviorRule.getUrlRegex()))) {
						urlBehaviorRuleMap.put(urlBehaviorRule.getUrlRegex(),
								urlBehaviorRule);
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

	@Override
	public UrlBehaviorRule findByUrlRegex(String urlRegex) {
		UrlBehaviorRule urlBehaviorRule = urlBehaviorRuleMap.get(urlRegex);
		return urlBehaviorRule;
	}

	@Override
	public UrlBehaviorRule findByUserBehavior(Integer userBehaviorId) {
		return null;
	}

	@Override
	public List<UrlBehaviorRule> findAll() {
		List<UrlBehaviorRule> urlBehaviorRules = new ArrayList<UrlBehaviorRule>(
				urlBehaviorRuleMap.values());
		return urlBehaviorRules;
	}
}
