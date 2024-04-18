package com.shaoxia.server.websocket.service.impl;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.alibaba.fastjson2.JSON;
import com.shaoxia.server.user.dao.GroupMemberDao;
import com.shaoxia.server.user.dao.RoomGroupDao;
import com.shaoxia.server.user.model.domain.GroupMember;
import com.shaoxia.server.user.model.vo.message.HistoryFriendMessageResp;
import com.shaoxia.server.websocket.dao.MessageDao;
import com.shaoxia.server.websocket.model.domain.Message;
import com.shaoxia.server.websocket.service.MessageService;
import com.shaoxia.server.websocket.vo.request.FriendChatReq;
import com.shaoxia.server.websocket.vo.request.GroupChatReq;
import com.shaoxia.server.websocket.vo.response.FriendChatResp;
import com.shaoxia.server.websocket.vo.response.GroupChatResp;
import com.shaoxia.server.websocket.vo.response.WSBaseResp;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.shaoxia.server.common.constant.RedisKey.*;
import static com.shaoxia.server.websocket.NettyWebSocketServer.CHANNEL_USER;
import static com.shaoxia.server.websocket.NettyWebSocketServer.ONLINE_USERS;

/**
 * @author wjc28
 * @version 1.0
 * @description: 消息发送业务层
 * @date 2024-04-14 14:18
 */
@Service
public class MessageServiceImpl implements MessageService {
	@Resource
	private RedisTemplate redisTemplate;
	@Resource
	private MessageDao messageDao;
	@Resource
	private GroupMemberDao groupMemberDao;
	@Resource
	private RoomGroupDao roomGroupDao;
	@Resource
	private SnowflakeGenerator generator;


	@Override
	public void friendChat(ChannelHandlerContext ctx, TextWebSocketFrame text) {
		FriendChatReq req = JSON.parseObject(text.text(), FriendChatReq.class);
		FriendChatResp resp = FriendChatResp.ReqTOResp(req);
		System.out.println(req);

		// 获取发送目标ID
		Long targetId = Long.parseLong(req.getTargetId());
		// 去在线用户列表里查找是否上线
		if (ONLINE_USERS.containsKey(targetId) && ONLINE_USERS.get(targetId).isActive()){
			// 对方在线就flush
			// TODO 这里如果刚刚好对面下线了，ONLINE_USERS就会移除对应id 这里就会报错
			Channel targetChannel = ONLINE_USERS.get(targetId);
			targetChannel.writeAndFlush(WSBaseResp.success(resp));
		}
		// 先存储数据到Redis，持久话使用定时任务
		saveFriendMessageToRedis(ctx,req);
		ctx.channel().writeAndFlush(new TextWebSocketFrame("消息发送成功"));

	}

	@Override
	public List<HistoryFriendMessageResp> getHistoryFriendMsg(String roomId) {
		return messageToResp(messageDao.getMessagesByRoomId(roomId));
	}

	@Override
	@Transactional
	public List<HistoryFriendMessageResp> getNotReadMessage(String roomId) {
		// 先从Redis里获取消息
		List<String> list = redisTemplate.opsForList().range(MESSAGE_LIST,0,-1);
		List<Message> messages = list.stream()
				.map(s -> {
					Message message = JSON.parseObject(s, Message.class);
					return message;
				})
				.filter(message -> {
					if (message.getType() == 1 && message.getRoomId() == Long.parseLong(roomId)) {
						return true;
					} else {
						return false;
					}
				}).collect(Collectors.toList());

		// 在从数据库里获取消息
		messages.addAll(messageDao.getNotRead(roomId));
		List<HistoryFriendMessageResp> resp = messageToResp(messages);
		messageDao.read(roomId);
		return resp;
	}

	/**
	 * 处理发送群聊消息
	 * @param ctx
	 * @param text
	 */
	@Override
	public void groupChat(ChannelHandlerContext ctx, TextWebSocketFrame text) {
		try{
			GroupChatReq req = JSON.parseObject(text.text(), GroupChatReq.class);
			String groupId = req.getGroupId();
			// 获取用户id列表
			List<Long> uidList = groupMemberDao.getMemberUid(groupId);
			uidList.forEach(uid -> {
				System.out.println("uid: "+uid);
				// 判断每一个用户是否上线
				if (ONLINE_USERS.containsKey(uid) && ONLINE_USERS.get(uid).isActive()){

					// 如果上线就flush
					GroupChatResp resp = GroupChatResp.ReqToResp(req);
					Channel channel = ONLINE_USERS.get(uid);
					channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(resp)));
				}
			});
			// 存储消息到Redis
			saveFriendMessageToRedis(ctx,req);
		}catch (Exception e){
			ctx.channel().writeAndFlush(new TextWebSocketFrame("发送异常"));
		}


	}

	/**
	 * 消息转响应
	 * @param messages
	 * @return
	 */
	private List<HistoryFriendMessageResp> messageToResp(List<Message> messages){
		return messages.stream().map(message -> {
			return HistoryFriendMessageResp.builder()
					.messageId(message.getId().toString())
					.msg(message.getContent())
					.type(message.getType().toString())
					.fromUid(message.getFromUid().toString())
					.build();
		}).collect(Collectors.toList());
	}

	/**
	 * 把单聊请求转化为消息实体类并存储到Redis
	 * @param ctx
	 * @param req
	 */
	private void saveFriendMessageToRedis(ChannelHandlerContext ctx,FriendChatReq req){
		Long fromId = CHANNEL_USER.get(ctx.channel()); // 发送者id
		Message cacheMessage = rawMessage(req, fromId);
		cacheMessage.setId(generator.next());
		System.out.println(cacheMessage+req.getMsg());
		redisTemplate.opsForList().leftPushAll(MESSAGE_LIST,JSON.toJSONString(cacheMessage));
		redisTemplate.expire(MESSAGE_LIST,2, TimeUnit.DAYS);
	}

	/**
	 * 重载，针对群聊消息
	 * @param ctx
	 * @param req
	 */
	private void saveFriendMessageToRedis(ChannelHandlerContext ctx,GroupChatReq req){
		Long fromId = CHANNEL_USER.get(ctx.channel()); // 发送者id
		Message cacheMessage = rawMessage(req, fromId);
		cacheMessage.setId(generator.next());
		redisTemplate.opsForList().leftPushAll(MESSAGE_LIST,JSON.toJSONString(cacheMessage));
		redisTemplate.expire(MESSAGE_LIST,2, TimeUnit.DAYS);
	}

	/**
	 * 用单聊请求类生成消息实体类
	 * @param req
	 * @param fromId
	 * @return
	 */
	private static Message rawMessage(FriendChatReq req,Long fromId){
		return  Message.builder()
				.fromUid(fromId)
				.roomId(Long.parseLong(req.getRoomId()))
				.isRead(0)
				.type(req.getType())
				.content(req.getMsg())
				.build();
	}

	private static Message rawMessage(GroupChatReq req,Long fromId){
		return  Message.builder()
				.fromUid(fromId)
				.roomId(Long.parseLong(req.getRoomId()))
				.isRead(0)
				.type(req.getType())
				.content(req.getMsg())
				.build();
	}



}
