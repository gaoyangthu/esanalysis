package com.github.gaoyangthu.esanalysis.meta.dao;

import com.github.gaoyangthu.esanalysis.meta.bean.UserBehaviorMeta;

public interface UserBehaviorMetaMapper {
	int deleteByPrimaryKey(Integer serialId);

	int insert(UserBehaviorMeta record);

	int insertSelective(UserBehaviorMeta record);

	UserBehaviorMeta selectByPrimaryKey(Integer serialId);

	int updateByPrimaryKeySelective(UserBehaviorMeta record);

	int updateByPrimaryKey(UserBehaviorMeta record);
}