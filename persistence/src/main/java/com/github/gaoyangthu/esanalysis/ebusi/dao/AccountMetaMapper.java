package com.github.gaoyangthu.esanalysis.ebusi.dao;

import com.github.gaoyangthu.esanalysis.ebusi.bean.AccountMeta;

import java.util.List;
import java.util.Map;

public interface AccountMetaMapper {
	int deleteByPrimaryKey(String accountId);

	int insert(AccountMeta record);

	int insertSelective(AccountMeta record);

	AccountMeta selectByPrimaryKey(String accountId);

	int updateByPrimaryKeySelective(AccountMeta record);

	int updateByPrimaryKey(AccountMeta record);

	List<AccountMeta> findByDate(Map<String, Object> params);
}