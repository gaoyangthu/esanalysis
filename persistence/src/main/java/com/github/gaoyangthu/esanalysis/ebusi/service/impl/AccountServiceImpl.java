package com.github.gaoyangthu.esanalysis.ebusi.service.impl;

import com.github.gaoyangthu.core.mysql.EbusiUtil;
import com.github.gaoyangthu.esanalysis.ebusi.bean.AccountMeta;
import com.github.gaoyangthu.esanalysis.ebusi.dao.AccountMetaMapper;
import com.github.gaoyangthu.esanalysis.ebusi.service.AccountService;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * account_meta表实现
 *
 * Author: gaoyangthu
 * Date: 14-3-12
 * Time: 下午3:12
 */
public class AccountServiceImpl implements AccountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

	/**
	 * 根据accountId查询用户信息
	 *
	 * @param accountId 用户ID
	 * @return 用户信息
	 */
	@Override
	public AccountMeta getAccount(String accountId) {
		AccountMeta accountMeta = null;

		SqlSession sqlSession = EbusiUtil.getSessionFactory().openSession();
		try {
			AccountMetaMapper accountMetaMapper = sqlSession.getMapper(AccountMetaMapper.class);
			accountMeta = accountMetaMapper.selectByPrimaryKey(accountId);
		} finally {
			sqlSession.close();
		}
		return accountMeta;
	}

	/**
	 * 根据日期查询注册用户
	 *
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return 用户列表
	 */
	@Override
	public List<AccountMeta> findByDate(Date beginDate, Date endDate) {
		List<AccountMeta> accounts = null;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);

		SqlSession sqlSession = EbusiUtil.getSessionFactory().openSession();
		try {
			AccountMetaMapper accountMetaMapper = sqlSession.getMapper(AccountMetaMapper.class);
			accounts = accountMetaMapper.findByDate(params);
		} finally {
			sqlSession.close();
		}

		return accounts;
	}
}
