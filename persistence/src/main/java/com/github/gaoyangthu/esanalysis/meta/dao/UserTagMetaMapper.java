package com.github.gaoyangthu.esanalysis.meta.dao;

import com.github.gaoyangthu.esanalysis.meta.bean.UserTagMeta;

public interface UserTagMetaMapper {
	int deleteByPrimaryKey(Integer serialId);

	int insert(UserTagMeta record);

	int insertSelective(UserTagMeta record);

	UserTagMeta selectByPrimaryKey(Integer serialId);

	int updateByPrimaryKeySelective(UserTagMeta record);

	int updateByPrimaryKey(UserTagMeta record);
}