package com.github.gaoyangthu.esanalysis.ebusi;

import java.util.Date;

/**
 * 数据统计类
 *
 * Author: gaoyangthu
 * Date: 2014/4/1
 * Time: 10:08
 */
public interface ESAnalyser {

	/**
	 * 分析数据库，统计数据，输出至展示库。
	 * <li>分析订单库</li>
	 * <li>分析交易库</li>
	 *
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	boolean analyse(Date beginDate, Date endDate);

}
