package com.github.gaoyangthu.esanalysis.meta.dao;

import com.github.gaoyangthu.esanalysis.meta.bean.LogParseRule;

public interface LogParseRuleMapper {
	int insert(LogParseRule record);

	int insertSelective(LogParseRule record);
}