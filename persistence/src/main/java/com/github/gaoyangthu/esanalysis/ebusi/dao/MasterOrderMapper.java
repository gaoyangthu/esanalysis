package com.github.gaoyangthu.esanalysis.ebusi.dao;

import com.github.gaoyangthu.esanalysis.ebusi.bean.MasterOrder;

import java.util.List;
import java.util.Map;

public interface MasterOrderMapper {
	int deleteByPrimaryKey(String masterOrderId);

	int insert(MasterOrder record);

	int insertSelective(MasterOrder record);

	MasterOrder selectByPrimaryKey(String masterOrderId);

	int updateByPrimaryKeySelective(MasterOrder record);

	int updateByPrimaryKey(MasterOrder record);

	List<MasterOrder> findByDate(Map<String, Object> params);
}