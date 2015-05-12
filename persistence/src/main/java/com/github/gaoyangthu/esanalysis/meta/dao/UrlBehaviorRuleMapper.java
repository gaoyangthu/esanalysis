package com.github.gaoyangthu.esanalysis.meta.dao;

import com.github.gaoyangthu.esanalysis.meta.bean.UrlBehaviorRule;

import java.util.List;

public interface UrlBehaviorRuleMapper {
	int deleteByPrimaryKey(Integer serialId);

	int insert(UrlBehaviorRule record);

	int insertSelective(UrlBehaviorRule record);

	UrlBehaviorRule selectByPrimaryKey(Integer serialId);

	int updateByPrimaryKeySelective(UrlBehaviorRule record);

	int updateByPrimaryKey(UrlBehaviorRule record);

	UrlBehaviorRule findByUrlRegex(String urlRegex);

	UrlBehaviorRule findByUserBehavior(Integer userBehaviorId);

	List<UrlBehaviorRule> findAll();
}