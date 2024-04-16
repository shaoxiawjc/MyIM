package com.shaoxia.server.websocket.handler;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import com.shaoxia.server.common.constant.WSReqCode;
import com.shaoxia.server.websocket.NettyWebSocketServer;
import com.shaoxia.server.websocket.service.impl.MessageServiceImpl;
import com.shaoxia.server.websocket.vo.request.WSBaseReq;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @author wjc28
 * @version 1.0
 * @description: TODO
 * @date 2024-04-14 13:55
 */
@Slf4j
@ChannelHandler.Sharable
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	private MessageServiceImpl messageService;

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		this.messageService = getMessageService();
	}

	private MessageServiceImpl getMessageService(){
		return SpringUtil.getBean(MessageServiceImpl.class);
	}

	// 处理异常
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.warn("异常发生，异常消息 ={}", cause);
		System.out.println(cause.getMessage());
		ctx.channel().close();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		Long id = NettyWebSocketServer.CHANNEL_USER.get(channel);
		NettyWebSocketServer.ONLINE_USERS.remove(id);
		NettyWebSocketServer.CHANNEL_USER.remove(id);
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext,
								TextWebSocketFrame textWebSocketFrame) throws Exception {
		Channel channel = channelHandlerContext.channel();
		if (!NettyWebSocketServer.CHANNEL_USER.containsKey(channel)){
			channelHandlerContext.channel().close();
			return;
		}
		WSBaseReq req = JSON.parseObject(textWebSocketFrame.text(), WSBaseReq.class);
		Integer code = req.getCode();
		log.info("\nmessage code is: {}",code);
		switch (WSReqCode.match(code)){
			case FRIEND_CHAT:
				messageService.friendChat(channelHandlerContext,textWebSocketFrame);
				break;
			case GROUP_CHAT:
				messageService.groupChat(channelHandlerContext,textWebSocketFrame);
				break;
			default:
				System.out.println("未知类型");
		}
	}
}
