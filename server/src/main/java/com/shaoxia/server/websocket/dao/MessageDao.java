package com.shaoxia.server.websocket.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxia.server.websocket.mapper.MessageMapper;
import com.shaoxia.server.websocket.model.domain.Message;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wjc28
 * @version 1.0
 * @description: 消息持久化
 * @date 2024-04-14 14:15
 */
@Component
public class MessageDao extends ServiceImpl<MessageMapper, Message> {
	@Resource
	private MessageMapper messageMapper;

	public List<Message> getMessagesByRoomId(String roomId){
		QueryWrapper<Message> wrapper = new QueryWrapper<>();
		wrapper.eq("room_id",roomId);
		return messageMapper.selectList(wrapper);
	}

	public List<Message> getNotRead(String roomId){
		QueryWrapper<Message> wrapper = new QueryWrapper<>();
		wrapper.eq("room_id",roomId)
				.eq("is_read",0);
		return messageMapper.selectList(wrapper);
	}

	public void read(String roomId){
		UpdateWrapper<Message> wrapper = new UpdateWrapper<>();
		wrapper.eq("room_id",roomId)
				.eq("is_read",0)
				.set("is_read",1);
		messageMapper.update(null,wrapper);
	}
}
