package com.shaoxia.server.user.service.impl;

import com.shaoxia.server.user.dao.UserFriendDao;
import com.shaoxia.server.user.model.vo.friend.GetFriendResp;
import com.shaoxia.server.user.service.FriendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wjc28
 * @version 1.0
 * @description: 好友业务实现类
 * @date 2024-04-13 19:01
 */
@Service
public class FriendServiceImpl implements FriendService {
	@Resource
	private UserFriendDao friendDao;
	@Override
	public List<GetFriendResp> getFriendList(String uid) {
		return friendDao.getFriendsByUid(uid);
	}
}
