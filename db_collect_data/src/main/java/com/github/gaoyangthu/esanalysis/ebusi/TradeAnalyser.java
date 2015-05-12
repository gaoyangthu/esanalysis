package com.github.gaoyangthu.esanalysis.ebusi;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: gaoyangthu
 * Date: 14-3-12
 * Time: 下午5:41
 */
@Deprecated
public interface TradeAnalyser {

	boolean analyseTrade(Date beginDate, Date endDate);

}
