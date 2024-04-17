package com.shaoxia.server.user.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxia.server.user.mapper.UserFriendMapper;
import com.shaoxia.server.user.model.domain.UserFriend;
import com.shaoxia.server.user.model.vo.friend.GetFriendResp;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wjc28
 * @version 1.0
 * @description: 用户持久层
 * @date 2024-04-13 17:44
 */
@Component
public class UserFriendDao extends ServiceImpl<UserFriendMapper, UserFriend> {
	@Resource
	private UserFriendMapper userFriendMapper;

	public List<GetFriendResp> getFriendsByUid(String uid){
		return userFriendMapper.selectFriendById(uid);
	}
}
