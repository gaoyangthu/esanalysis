package com.github.gaoyangthu.esanalysis.ebusi.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.gaoyangthu.esanalysis.ebusi.AccountAnalyser;
import com.github.gaoyangthu.esanalysis.ebusi.bean.AccountMeta;
import com.github.gaoyangthu.esanalysis.ebusi.service.AccountService;
import com.github.gaoyangthu.esanalysis.ebusi.service.impl.AccountServiceImpl;
import com.github.gaoyangthu.esanalysis.hbase.service.UserIdService;
import com.github.gaoyangthu.esanalysis.hbase.service.impl.UserIdServiceImpl;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-12
 * Time: 下午3:12
 */
public class AccountAnalyserImpl implements AccountAnalyser {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountAnalyserImpl.class);

	private AccountService accountService;

	private UserIdService userIdService;

	public AccountAnalyserImpl() {
		accountService = new AccountServiceImpl();

		userIdService = new UserIdServiceImpl();
	}

	@Override
	public boolean analyseAccount(Date beginDate, Date endDate) {
		boolean flag = false;

		List<AccountMeta> accounts = accountService.findByDate(beginDate, endDate);
		if(accounts == null) {
			return false;
		}

		for (AccountMeta account : accounts) {
			if (account != null) {
				boolean f = processAccount(account);
				if (!f) {
					LOGGER.error("Get bdUserUuid error. accountId={}",
							account.getAccountId());
				}
			}
			flag = true;
		}

		return flag;
	}

	private boolean processAccount(AccountMeta account) {
		String accountId = account.getAccountId();

		String bdUserUuid = userIdService.getBdUserUuid(null, null, accountId);
		LOGGER.debug("Get bdUserUuid. bdUserUuid={}, accountId={}", bdUserUuid,
				accountId);

		return StringUtils.isNotBlank(bdUserUuid);
	}
}
