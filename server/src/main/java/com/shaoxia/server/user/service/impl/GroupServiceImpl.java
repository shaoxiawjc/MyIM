package com.shaoxia.server.user.service.impl;

import com.shaoxia.server.common.exception.BusinessException;
import com.shaoxia.server.common.utils.IdUtils;
import com.shaoxia.server.user.dao.GroupMemberDao;
import com.shaoxia.server.user.dao.RoomDao;
import com.shaoxia.server.user.dao.RoomGroupDao;
import com.shaoxia.server.user.model.domain.GroupMember;
import com.shaoxia.server.user.model.domain.Room;
import com.shaoxia.server.user.model.domain.RoomGroup;
import com.shaoxia.server.user.model.vo.group.CreateGroupResp;
import com.shaoxia.server.user.model.vo.group.GetMembersResp;
import com.shaoxia.server.user.service.GroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.shaoxia.server.common.exception.ErrorCode.*;

/**
 * @author wjc28
 * @version 1.0
 * @description: 群聊业务实现类
 * @date 2024-04-15 8:07
 */
@Service
public class GroupServiceImpl implements GroupService {
	@Resource
	private RoomGroupDao roomGroupDao;
	@Resource
	private GroupMemberDao groupMemberDao;
	@Resource
	private RoomDao roomDao;

	@Override
	@Transactional
	public CreateGroupResp createGroup(String groupName, String uid) {
		// 创建一个聊天室
		Room room = new Room();
		room.setType(2);
		roomDao.save(room);
		// 创建一个群聊
		RoomGroup roomGroup = new RoomGroup();
		roomGroup.setRoomId(room.getId());
		roomGroup.setName(groupName);
		roomGroupDao.save(roomGroup);
		// 创建一个群聊成员
		GroupMember groupMember = new GroupMember();
		groupMember.setGroupId(roomGroup.getId());
		groupMember.setUid(Long.parseLong(uid));
		groupMember.setRole(1); // 设置为群主
		groupMemberDao.save(groupMember);
		// 包装响应类
		return CreateGroupResp.builder()
				.groupName(groupName)
				.groupRoomId(room.getId().toString())
				.groupId(roomGroup.getId().toString())
				.build();
	}

	@Override
	public List<GetMembersResp> getMembers(String groupId) {
		return groupMemberDao.getMembersByGroupId(groupId)
				.stream()
				.map(groupMember -> {
					return GetMembersResp.builder()
							.uid(groupMember.getUid().toString())
							.role(groupMember.getRole().toString())
							.build();
				}).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void enterGroup(String groupId, String uid) {
		// 参数校验
		if (!IdUtils.isValidNumericString(groupId,uid)){
			throw new BusinessException(PARAMS_ERROR,"参数不合法");
		}
		// 先判断该用户是否已经在此群聊里
		if (Objects.isNull(groupMemberDao.getByGroupIdAndUid(groupId,uid))){
			// 插入一条成员信息
			groupMemberDao.add(groupId,uid);
		}else {
			throw new BusinessException(REPETITION_OPERATION,"已经加入过该群聊");
		}
	}

	@Override
	public void updateRole(String groupId, String updateRole, String updateUid, String uid) {
		// 参数检验
		Integer parseInt = -1;
		if (!IdUtils.isValidStringToInteger(updateRole)){
			throw new BusinessException(PARAMS_ERROR,"参数不合法");
		}else {
			parseInt = Integer.parseInt(updateRole);
			if (!(parseInt == 1 || parseInt == 2 || parseInt == 3)){
				throw new BusinessException(PARAMS_ERROR,"参数不合法");
			}
		}
		if (!IdUtils.isValidNumericString(groupId,updateUid,uid)){
			throw new BusinessException(PARAMS_ERROR,"参数不合法");
		}
		// 判断用户是否有权限设置
		GroupMember isBoss = groupMemberDao.getByGroupIdAndUid(groupId, uid);
		if (isBoss.getRole() != 1){
			throw new BusinessException(NO_AUTH,"您不是群主");
		}
		// 设置成员权限
		GroupMember needUpdate = groupMemberDao.getByGroupIdAndUid(groupId, updateUid);
		if (Objects.isNull(needUpdate)){
			throw new BusinessException(NULL_ERROR,"要修改的用户为空");
		}
		needUpdate.setRole(parseInt);
		groupMemberDao.updateRole(needUpdate);
	}


}
