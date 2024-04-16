package com.shaoxia.server.user.service;

import com.shaoxia.server.user.model.vo.friend.GetFriendResp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FriendService {
	List<GetFriendResp> getFriendList(String uid);
}
