package edu.xidian.hgy.server.handler;

import edu.xidian.hgy.message.RpcRequestMessage;
import edu.xidian.hgy.message.RpcResponseMessage;
import edu.xidian.hgy.server.service.HelloService;
import edu.xidian.hgy.server.service.ServicesFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Description
 * @Author He
 * @Date 2021/4/16 15:14
 */
@Slf4j
@ChannelHandler.Sharable
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage message) {
		RpcResponseMessage response = new RpcResponseMessage();
		response.setSequenceId(message.getSequenceId());
		try {
			HelloService service = (HelloService)
					ServicesFactory.getService(Class.forName(message.getInterfaceName()));
			Method method = service.getClass().getMethod(message.getMethodName(), message.getParameterTypes());
			Object res = method.invoke(service, message.getParameterValue());
			response.setReturnValue(res);
		} catch (Exception e) {
			e.printStackTrace();
			String msg = e.getCause().getMessage();
			response.setExceptionValue(new Exception("远程调用出错:" + msg));
		}
		ctx.writeAndFlush(response);
	}
}
