package com.shaoxia.server.websocket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shaoxia.server.websocket.model.domain.Message;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

/**
* @author wjc28
* @description 针对表【message(消息表)】的数据库操作Mapper
* @createDate 2024-04-14 14:13:14
* @Entity generator.domain.Message
*/
@Mapper
@Repository
public interface MessageMapper extends BaseMapper<Message> {

}




