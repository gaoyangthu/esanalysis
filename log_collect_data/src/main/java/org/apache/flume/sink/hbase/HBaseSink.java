package org.apache.flume.sink.hbase;

import com.github.gaoyangthu.esanalysis.logparse.service.LogParser;
import com.google.common.base.Preconditions;
import org.apache.commons.io.IOUtils;
import org.apache.flume.conf.Configurable;
import org.apache.flume.instrumentation.SinkCounter;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Author: Gao Yang
 * Date: 14-4-1
 */
public class HBaseSink extends AbstractSink implements Configurable {
    /**
     * The constant logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HBaseSink.class);

    /**
     * The sink counter
     */
    private SinkCounter sinkCounter;

    /**
     * The size of batch
     */
    private Integer batchSize;

    /**
     * The encoding of log
     */
    private String encoding;

    /**
     * Process.
     * @return status
     * @throws EventDeliveryException
     * 
     * TODO 在框架内的 channel transaction，能否用上多线程
     * TODO 建议 start() , stop() 和  周期心跳  消息到一个 监控中央，  
     * CV_ID:X001 
     */
    @Override
    public Status process() throws EventDeliveryException {
        Channel channel = getChannel();
        Transaction transaction = channel.getTransaction();

        try {
            transaction.begin();

            int txnEventCount = 0;
            for (txnEventCount = 0; txnEventCount < batchSize; txnEventCount++) {
                Event event = channel.take();
                if (event == null) {
                    break;
                }

                // send event messages to log parser
                LogParser logParser = new LogParser();
                logParser.parseLog(IOUtils.toInputStream(new String(event.getBody())));
                LOGGER.info(new String(event.getBody()));
            }

            if (txnEventCount == 0) {
                sinkCounter.incrementBatchEmptyCount();
            } else if (txnEventCount == batchSize) {
                sinkCounter.incrementBatchCompleteCount();
            } else {
                sinkCounter.incrementBatchUnderflowCount();
            }

            transaction.commit();

            if (txnEventCount < 0) {
                return Status.BACKOFF;
            } else {
                sinkCounter.addToEventDrainSuccessCount(txnEventCount);
                return Status.READY;
            }
        } catch (Exception e) {
            LOGGER.error("HBase Sink Exception:{}", e);
            transaction.rollback();
            return Status.BACKOFF;
        } catch (Throwable t) {
            transaction.rollback();
            LOGGER.error("HBase Sink Throws Error");

            if (t instanceof Error) {
                throw (Error) t;
            } else {
                throw new EventDeliveryException(t);
            }
        } finally {
            transaction.close();
        }
    }

    /**
     * Configure void.
     * @param context the context
     */
    @Override
    public void configure(Context context) {
        sinkCounter = new SinkCounter(this.getName());
        batchSize = context.getInteger("hbase.batchSize", 1000);
        encoding = context.getString("custom.encoding", "UTF-8");
        Preconditions.checkArgument(batchSize > 0, "batchSize must be greater than 0");
    }

    /**
     * Start void.
     */
    @Override
    public void start () {
        LOGGER.info("HBase sink starting");
        sinkCounter.start();
        super.start();
    }

    /**
     * Stop void.
     */
    @Override
    public synchronized void stop () {
        LOGGER.info("Kafka sink {} stopping", this.getName());
        sinkCounter.stop();
        super.stop();
    }
}
