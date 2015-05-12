package com.github.gaoyangthu.esanalysis.logreport.mapred;

import com.github.gaoyangthu.core.util.TimeUtils;
import com.github.gaoyangthu.esanalysis.report.service.EbusiDailyReportService;
import com.github.gaoyangthu.esanalysis.report.service.impl.EbusiDailyReportServiceImpl;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.util.LineReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by IntelliJ IDEA
 * Project: esanalysis
 * Author: GaoYang
 * Date: 2014/4/11 0011
 */
public class FileParser {
    /**
     * The constant logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FileParser.class);

    public FileParser() {
    }

    /**
     * Parse a output file of reducer to update the database
     * @param path
     * @return
     */
    public static boolean parseOutput(Path path) {
        Configuration conf = new Configuration();
        FileSystem fileSystem = null;
        FSDataInputStream inputStream = null;
        try {
            fileSystem = FileSystem.get(conf);
            if (fileSystem.exists(path)) {
                inputStream = fileSystem.open(path);
                LineReader reader = new LineReader(inputStream);
                Text line = new Text();
                while(reader.readLine(line) > 0) {
                    String[] refs = line.toString().split(",");
                    if (refs.length == 5) {
                        String bdUserUuid = refs[0];
                        Date date = new Date(Long.valueOf(refs[1]));
	                    date = TimeUtils.getTimeAtStartOfDay(date);
                        String userChannelTag = refs[2];
                        Date firstChannelVisit = new Date(Long.valueOf(refs[3]));
                        Integer pageview = Integer.valueOf(refs[4]);
                        EbusiDailyReportService service = new EbusiDailyReportServiceImpl();
                        service.updateByLog(bdUserUuid, date, userChannelTag, firstChannelVisit, pageview);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Exception: ", e);
            return false;
        }
        return true;
    }
}
