package com.shaoxia.server.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shaoxia.server.user.model.domain.UserFriend;
import com.shaoxia.server.user.model.vo.friend.GetFriendResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author wjc28
* @description 针对表【user_friend(用户联系人表)】的数据库操作Mapper
* @createDate 2024-04-13 17:00:42
* @Entity generator.domain.UserFriend
*/
@Mapper
@Repository
public interface UserFriendMapper extends BaseMapper<UserFriend> {
	List<GetFriendResp> selectFriendById(@Param("uid") String uid);
}




