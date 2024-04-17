package com.shaoxia.server.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxia.server.user.mapper.RoomFriendMapper;

import com.shaoxia.server.user.model.domain.RoomFriend;
import org.springframework.stereotype.Component;

/**
 * @author wjc28
 * @version 1.0
 * @description: 好友房间持久化层
 * @date 2024-04-14 16:41
 */
@Component
public class RoomFriendDao extends ServiceImpl<RoomFriendMapper, RoomFriend> {
}
