package com.shaoxia.server.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxia.server.user.mapper.RoomGroupMapper;
import com.shaoxia.server.user.model.domain.RoomGroup;
import org.springframework.stereotype.Component;

/**
 * @author wjc28
 * @version 1.0
 * @description: 群聊房间持久话层
 * @date 2024-04-15 8:09
 */
@Component
public class RoomGroupDao extends ServiceImpl<RoomGroupMapper, RoomGroup> {
}
