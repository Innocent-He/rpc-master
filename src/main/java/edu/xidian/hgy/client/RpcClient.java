package edu.xidian.hgy.client;

import edu.xidian.hgy.client.handler.RpcResponseMessageHandler;
import edu.xidian.hgy.message.RpcRequestMessage;
import edu.xidian.hgy.protocol.MessageCodecSharable;
import edu.xidian.hgy.protocol.ProcotolFrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author He
 * @Date 2021/4/16 15:27
 */
@Slf4j
public class RpcClient {
	public static void main(String[] args) {
		NioEventLoopGroup group = new NioEventLoopGroup();
		LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
		MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
		RpcResponseMessageHandler RPC_HANDLER = new RpcResponseMessageHandler();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.group(group);
			bootstrap.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new ProcotolFrameDecoder());
					ch.pipeline().addLast(LOGGING_HANDLER);
					ch.pipeline().addLast(MESSAGE_CODEC);
					ch.pipeline().addLast(RPC_HANDLER);
				}
			});
			Channel channel = bootstrap.connect("localhost", 8080).sync().channel();

			ChannelFuture future = channel.writeAndFlush(new RpcRequestMessage(
					1,
					"edu.xidian.hgy.server.service.HelloService",
					"sayHello",
					String.class,
					new Class[]{String.class},
					new Object[]{"张三"}
			)).addListener(promise -> {
				if (!promise.isSuccess()) {
					Throwable cause = promise.cause();
					log.error("error", cause);
				}
			});

			channel.closeFuture().sync();
		} catch (Exception e) {
			log.error("client error", e);
		} finally {
			group.shutdownGracefully();
		}
	}
}
