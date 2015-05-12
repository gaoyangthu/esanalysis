package com.github.gaoyangthu.esanalysis.report.dao;

import com.github.gaoyangthu.esanalysis.report.bean.EbusiDailyReport;
import com.github.gaoyangthu.esanalysis.report.bean.EbusiDailyReportKey;

public interface EbusiDailyReportMapper {
	int deleteByPrimaryKey(EbusiDailyReportKey key);

	int insert(EbusiDailyReport record);

	int insertSelective(EbusiDailyReport record);

	EbusiDailyReport selectByPrimaryKey(EbusiDailyReportKey key);

	int updateByPrimaryKeySelective(EbusiDailyReport record);

	int updateByPrimaryKey(EbusiDailyReport record);
}