package com.shaoxia.server.websocket.handler;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.extra.spring.SpringUtil;
import com.shaoxia.server.common.utils.JwtUtils;
import com.shaoxia.server.websocket.NettyWebSocketServer;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.Optional;

import static com.shaoxia.server.common.constant.RedisKey.TOKEN;
import static com.shaoxia.server.websocket.NettyWebSocketServer.CHANNEL_USER;
import static com.shaoxia.server.websocket.NettyWebSocketServer.ONLINE_USERS;

/**
 * @author wjc28
 * @version 1.0
 * @description: 连接websocket时鉴权
 * @date 2024-04-12 23:56
 */
@Slf4j
public class HttpHeadersHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 判断是否是Http请求，即第一次连接时的认证
		if (msg instanceof FullHttpRequest){
			FullHttpRequest request = (FullHttpRequest) msg;
			UrlBuilder urlBuilder = UrlBuilder.ofHttp(request.uri());

			// 获取token参数
			String token = request.headers().get("token");

			RedisTemplate redisTemplate = SpringUtil.getBean("redisTemplate");
			try {
				// 如果header里没有token，就提醒要登录
				if (token.isEmpty()){
					sendErrorResponse(ctx);
				}
				// 获取token里的id
				String id = JwtUtils.verify(token).getClaim("id").asString();
				// 判断是否有id
				if (Objects.isNull(id) || id.isEmpty()){
					sendErrorResponse(ctx);
				}else {
					// 从Redis里获取token
					String redisToken = (String) redisTemplate.opsForValue().get(TOKEN + id);
					// 如果没有，就提醒用户先登录
					if (Objects.isNull(redisToken) || redisToken.isEmpty()){
						sendErrorResponse(ctx);
					}
					// 判断token是否匹配
					if (!redisToken.equals(token)){
						sendErrorResponse(ctx);
					}
					// Redis里有，就代表已经登录过了
					// 判断是否有其他人在线，如果有，就把之前正在登录的人下线
					if (ONLINE_USERS.containsKey(id)){
						// 从在线用户列表移除后
						// 上一个channel在发送消息到NettyWebSocketServerHandler会被下线
						Channel preChannel = ONLINE_USERS.get(id);
						CHANNEL_USER.remove(preChannel);
						ONLINE_USERS.remove(id);
					}
					ONLINE_USERS.put(Long.parseLong(id),ctx.channel());
					CHANNEL_USER.put(ctx.channel(),Long.parseLong(id));
					System.out.println(ONLINE_USERS);
					System.out.println(CHANNEL_USER);
					ctx.fireChannelRead(msg);
				}
			}catch (Exception e){
				System.out.println("---------------");
				e.printStackTrace();
				sendErrorResponse(ctx);
			}
		}else {
			ctx.fireChannelRead(msg);
		}
	}

	private void sendErrorResponse(ChannelHandlerContext ctx) {
		// Create an error response
		DefaultFullHttpResponse response = new DefaultFullHttpResponse(
				HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST,
				Unpooled.copiedBuffer("Connection refused", CharsetUtil.UTF_8));

		// Close the connection after sending the response
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
}
