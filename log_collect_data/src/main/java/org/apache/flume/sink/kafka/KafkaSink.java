 package org.apache.flume.sink.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.conf.Configurable;
import org.apache.flume.instrumentation.SinkCounter;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * Created with IntelliJ IDEA.
 * Author: Gao Yang
 * Date: 14-3-27
 */
public class KafkaSink extends AbstractSink implements Configurable {
    /**
     * The constant logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSink.class);

    /**
     *
     */
    private SinkCounter sinkCounter;

    /**
     * The producer of kafka
     */
    private Producer<String, String> producer;

    /**
     * The topic of flume-ng registered in zookeeper.
     */
    private String topic;

    /**
     * The size of batch
     */
    private Integer batchSize;

    /**
     * The list of messages
     */
    private List<KeyedMessage<String, String>> batch;

    /**
     * Process.
     * @return status
     * @throws EventDeliveryException
     */
    @Override
    public Status process() throws EventDeliveryException {
        Status status = Status.READY;
        Channel channel = getChannel();
        Transaction transaction = channel.getTransaction();

        try {
            // firstly start the transaction
            transaction.begin();

            batch.clear();

            int txnEventCount = 0;
            for (txnEventCount = 0; txnEventCount < batchSize; txnEventCount++) {
                Event event = channel.take();
                if (event == null) {
                    break;
                }
                batch.add(new KeyedMessage<String, String>(topic, new String(event.getBody())));
                LOGGER.trace("Message: {}", event.getBody());
            }

            if (!batch.isEmpty()) {
                producer.send(batch);
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
                status = Status.BACKOFF;
            } else {
                sinkCounter.addToEventDrainSuccessCount(txnEventCount);
                status = Status.READY;
            }
        } catch (Exception e) {
            LOGGER.error("KafkaSink Exception:{}", e);
            transaction.rollback();
            status = Status.BACKOFF;
        } finally {
            transaction.close();
        }

        return status;
    }

    /**
     * Configure void.
     * @param context the context
     */
    @Override
    public void configure(Context context) {
        topic = context.getString("topic");
        Preconditions.checkState(topic != null, "No Kafka topic specified");

        // default batch size is 1000
        batchSize = context.getInteger("batchSize", 1000);
        batch = new ArrayList<KeyedMessage<String, String>>(batchSize);

        // get parameters from property file
        Properties props = new Properties();
        Map<String, String> contextMap = context.getParameters();
        for (String key : contextMap.keySet()) {
            if (!key.equals("type") && !key.equals("channel")) {
                props.setProperty(key, context.getString(key));
                LOGGER.info("key={}, value={}", key, context.getString(key));
            }
        }
        producer = new Producer<String, String>(new ProducerConfig(props));
        sinkCounter = new SinkCounter(this.getName());
    }

    /**
     * Start void.
     */
    @Override
    public void start() {
        LOGGER.info("Kafka sink starting");
        sinkCounter.start();
        super.start();
    }

    /**
     * Stop void.
     */
    @Override
    public synchronized void stop() {
        LOGGER.info("Kafka sink {} stopping", this.getName());
        producer.close();
        sinkCounter.stop();
        super.stop();
    }
}
