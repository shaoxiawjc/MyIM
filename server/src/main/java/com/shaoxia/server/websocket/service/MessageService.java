package com.shaoxia.server.websocket.service;


import com.shaoxia.server.user.model.vo.message.HistoryFriendMessageResp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {
	void friendChat(ChannelHandlerContext ctx, TextWebSocketFrame text);


	List<HistoryFriendMessageResp> getHistoryFriendMsg(String roomId);

	List<HistoryFriendMessageResp> getNotReadMessage(String roomId);

	void groupChat(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame);

}
