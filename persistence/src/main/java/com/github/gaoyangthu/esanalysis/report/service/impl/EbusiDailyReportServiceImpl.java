package com.github.gaoyangthu.esanalysis.report.service.impl;

import com.github.gaoyangthu.core.mysql.ReportUtil;
import com.github.gaoyangthu.esanalysis.report.bean.EbusiDailyReport;
import com.github.gaoyangthu.esanalysis.report.bean.EbusiDailyReportKey;
import com.github.gaoyangthu.esanalysis.report.dao.EbusiDailyReportMapper;
import com.github.gaoyangthu.esanalysis.report.service.EbusiDailyReportService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * EbusiDailyReport实现
 *
 * Author: gaoyangthu
 * Date: 2014/3/31
 * Time: 17:22
 */
public class EbusiDailyReportServiceImpl implements EbusiDailyReportService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EbusiDailyReportServiceImpl.class);

	private EbusiDailyReportMapper ebusiDailyReportMapper;

	/**
	 * 创建EbusiDailyReport
	 *
	 * @param ebusiDailyReport
	 * @return
	 */
	@Override
	public boolean create(EbusiDailyReport ebusiDailyReport) {
		if(ebusiDailyReport == null) {
			return false;
		}

		boolean flag = false;
		SqlSession sqlSession = ReportUtil.getSessionFactory().openSession();
		try {
			EbusiDailyReportMapper ebusiDailyReportMapper = sqlSession.getMapper(EbusiDailyReportMapper.class);
			int rows = ebusiDailyReportMapper.insertSelective(ebusiDailyReport);
			sqlSession.commit();
			flag = rows > 0;
		} finally {
			sqlSession.close();
		}
		return flag;
	}

	/**
	 * 根据主键查询EbusiDailyReport
	 *
	 * @param bdUserUuid
	 * @param date
	 * @return
	 */
	@Override
	public EbusiDailyReport get(String bdUserUuid, Date date) {
		if(StringUtils.isBlank(bdUserUuid) || date == null) {
			return null;
		}

		EbusiDailyReportKey ebusiDailyReportKey = new EbusiDailyReportKey();
		ebusiDailyReportKey.setBdUserUuid(bdUserUuid);
		ebusiDailyReportKey.setDate(date);

		EbusiDailyReport ebusiDailyReport = null;
		SqlSession sqlSession = ReportUtil.getSessionFactory().openSession();
		try {
			EbusiDailyReportMapper ebusiDailyReportMapper = sqlSession.getMapper(EbusiDailyReportMapper.class);
			ebusiDailyReport = ebusiDailyReportMapper.selectByPrimaryKey(ebusiDailyReportKey);
		} finally {
			sqlSession.close();
		}

		return ebusiDailyReport;
	}

	/**
	 * 更新EbusiDailyReport
	 *
	 * @param ebusiDailyReport
	 * @return
	 */
	@Override
	public boolean update(EbusiDailyReport ebusiDailyReport) {
		if(ebusiDailyReport == null) {
			return false;
		}

		boolean flag = false;
		SqlSession sqlSession = ReportUtil.getSessionFactory().openSession();
		try {
			EbusiDailyReportMapper ebusiDailyReportMapper = sqlSession.getMapper(EbusiDailyReportMapper.class);
			int rows = ebusiDailyReportMapper.updateByPrimaryKeySelective(ebusiDailyReport);
			sqlSession.commit();
			flag = rows > 0;
		} finally {
			sqlSession.close();
		}
		return flag;
	}

	/**
	 * 从日志更新EbusiDailyReport
	 *
	 * @param bdUserUuid
	 * @param date
	 * @param userChannelTag
	 * @param firstChannelVisit
	 * @param pv
	 * @return
	 */
	@Override
	public boolean updateByLog(String bdUserUuid, Date date, String userChannelTag, Date firstChannelVisit, Integer pv) {
		boolean flag = false;
		if(StringUtils.isBlank(bdUserUuid) || date == null) {
			LOGGER.error("The bdUserUuid|date is null.");
			return false;
		}

		EbusiDailyReport ebusiDailyReport = get(bdUserUuid, date);
		if(ebusiDailyReport != null) {
			ebusiDailyReport.setUserChannelTag(userChannelTag);
			ebusiDailyReport.setFirstChannelVisit(firstChannelVisit);
			ebusiDailyReport.setPv(pv);

			flag = update(ebusiDailyReport);
		} else {
			ebusiDailyReport = new EbusiDailyReport();
			ebusiDailyReport.setBdUserUuid(bdUserUuid);
			ebusiDailyReport.setDate(date);

			ebusiDailyReport.setUserChannelTag(userChannelTag);
			ebusiDailyReport.setFirstChannelVisit(firstChannelVisit);
			ebusiDailyReport.setPv(pv);

			flag = create(ebusiDailyReport);
		}

		return flag;
	}

	/**
	 * 从数据更新EbusiDailyReport
	 *
	 * @param ebusiDailyReport
	 * @return
	 */
	@Override
	public boolean updateByDB(EbusiDailyReport ebusiDailyReport) {
		boolean flag = false;
		if(ebusiDailyReport == null) {
			LOGGER.error("The ebusiDailyReport is null.");
			return false;
		}

		if(get(ebusiDailyReport.getBdUserUuid(), ebusiDailyReport.getDate()) != null) {
			flag = update(ebusiDailyReport);
		} else {
			flag = create(ebusiDailyReport);
		}
		return flag;
	}
}
