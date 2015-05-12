package org.apache.flume.source.netflow;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.flume.ChannelException;
import org.apache.flume.Context;
import org.apache.flume.CounterGroup;
import org.apache.flume.Event;
import org.apache.flume.EventDrivenSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.conf.Configurables;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.source.AbstractSource;
import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.oio.OioDatagramChannelFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Netflow的Flume source
 * Author: gaoyangthu
 * Date: 2014/5/7
 * Time: 14:54
 */
public class NetflowSource extends AbstractSource implements EventDrivenSource,
		Configurable {

	public static final String CONFIG_PORT = "port";

	public static final String CONFIG_HOST = "host";

	public static final String SRC_IP = "src_ip";

	public static final String SRC_PORT = "src_port";

	private int port = 30002;
	private int maxsize = 1 << 16; // 64k is max allowable in RFC 5426
	private String host = null;
	private Channel nettyChannel;

	private static final Logger logger = LoggerFactory.getLogger(NetflowSource.class);

	private CounterGroup counterGroup = new CounterGroup();

	public class NetflowHandler extends SimpleChannelHandler {

		@Override
		public void messageReceived(ChannelHandlerContext ctx,
				MessageEvent mEvent) {
			try {
				InetSocketAddress address = (InetSocketAddress) mEvent.getRemoteAddress();
				Map<String, String> headers = new HashMap<String, String>(2);
				headers.put(SRC_IP, address.getHostName());
				headers.put(SRC_PORT, String.valueOf(address.getPort()));

				ChannelBuffer buffer = (ChannelBuffer) mEvent.getMessage();
				ByteBuffer byteBuffer = buffer.copy().toByteBuffer();
				byte[] bytes = byteBuffer.array();
				Event e = EventBuilder.withBody(bytes, headers);
				if (e == null) {
					return;
				}
				getChannelProcessor().processEvent(e);
				counterGroup.incrementAndGet("events.success");
			} catch (ChannelException ex) {
				counterGroup.incrementAndGet("events.dropped");
				logger.error("Error writting to channel", ex);
				return;
			}
		}
	}

	@Override
	public void start() {
		// setup Netty server
		ConnectionlessBootstrap serverBootstrap = new ConnectionlessBootstrap(
				new OioDatagramChannelFactory(Executors.newCachedThreadPool()));
		serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() {
				return Channels.pipeline(new NetflowHandler());
			}
		});

		serverBootstrap.setOption(
				"receiveBufferSizePredictorFactory",
				new FixedReceiveBufferSizePredictorFactory(maxsize));

		if (host == null) {
			nettyChannel = serverBootstrap.bind(new InetSocketAddress(port));
		} else {
			nettyChannel = serverBootstrap.bind(new InetSocketAddress(host,
					port));
		}

		super.start();
	}

	@Override
	public void stop() {
		logger.info("Netflow UDP Source stopping...");
		logger.info("Metrics:{}", counterGroup);
		if (nettyChannel != null) {
			nettyChannel.close();
			try {
				nettyChannel.getCloseFuture().await(60, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				logger.warn("netty server stop interrupted", e);
			} finally {
				nettyChannel = null;
			}
		}

		super.stop();
	}

	@Override
	public void configure(Context context) {
		Configurables.ensureRequiredNonNull(context, CONFIG_PORT);
		port = context.getInteger(CONFIG_PORT);
		host = context.getString(CONFIG_HOST);
	}

}
