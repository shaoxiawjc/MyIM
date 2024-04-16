package com.shaoxia.server.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxia.server.user.mapper.RoomMapper;
import com.shaoxia.server.user.mapper.UserMapper;
import com.shaoxia.server.user.model.domain.Room;
import com.shaoxia.server.user.model.domain.User;
import org.springframework.stereotype.Component;

/**
 * @author wjc28
 * @version 1.0
 * @description: TODO
 * @date 2024-04-14 16:40
 */
@Component
public class RoomDao extends ServiceImpl<RoomMapper, Room> {

}
