package com.shaoxia.server.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shaoxia.server.user.model.domain.RoomGroup;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author wjc28
* @description 针对表【room_group(群聊房间表)】的数据库操作Mapper
* @createDate 2024-04-15 07:58:25
* @Entity generator.domain.RoomGroup
*/
@Mapper
@Repository
public interface RoomGroupMapper extends BaseMapper<RoomGroup> {

}




