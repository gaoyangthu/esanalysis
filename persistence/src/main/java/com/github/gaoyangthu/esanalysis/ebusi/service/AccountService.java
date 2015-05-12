package com.github.gaoyangthu.esanalysis.ebusi.service;

import java.util.Date;
import java.util.List;

import com.github.gaoyangthu.esanalysis.ebusi.bean.AccountMeta;

/**
 * account_meta表接口
 *
 * Author: gaoyangthu
 * Date: 14-3-12
 * Time: 下午3:12
 */
public interface AccountService {

	/**
	 * 根据accountId查询用户信息
	 *
	 * @param accountId 用户ID
	 * @return 用户信息
	 */
	AccountMeta getAccount(String accountId);

	/**
	 * 根据日期查询注册用户
	 *
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return 用户列表
	 */
	List<AccountMeta> findByDate(Date beginDate, Date endDate);

}
