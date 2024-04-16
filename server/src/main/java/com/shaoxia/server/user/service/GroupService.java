package com.shaoxia.server.user.service;

import com.shaoxia.server.user.model.vo.group.CreateGroupResp;
import com.shaoxia.server.user.model.vo.group.GetMembersResp;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GroupService {
	CreateGroupResp createGroup(String groupName, String uid);

	List<GetMembersResp> getMembers(String groupId);

	void enterGroup(String groupId, String uid);

	void updateRole(String groupId, String updateRole, String updateUid, String uid);
}
