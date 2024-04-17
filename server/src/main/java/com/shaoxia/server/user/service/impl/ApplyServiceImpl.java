package com.shaoxia.server.user.service.impl;

import com.shaoxia.server.common.exception.BusinessException;
import com.shaoxia.server.common.utils.IdUtils;
import com.shaoxia.server.user.dao.RoomDao;
import com.shaoxia.server.user.dao.RoomFriendDao;
import com.shaoxia.server.user.dao.UserApplyDao;
import com.shaoxia.server.user.dao.UserFriendDao;
import com.shaoxia.server.user.model.domain.Room;
import com.shaoxia.server.user.model.domain.RoomFriend;
import com.shaoxia.server.user.model.domain.UserApply;
import com.shaoxia.server.user.model.domain.UserFriend;
import com.shaoxia.server.user.model.vo.apply.GetApplyResp;
import com.shaoxia.server.user.service.ApplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.shaoxia.server.common.constant.RoomType.FRIEND;
import static com.shaoxia.server.common.exception.ErrorCode.*;

/**
 * @author wjc28
 * @version 1.0
 * @description: 好友申请业务实现类
 * @date 2024-04-13 16:25
 */
@Service
public class ApplyServiceImpl implements ApplyService {
	@Resource
	private UserApplyDao userApplyDao;
	@Resource
	private UserFriendDao userFriendDao;
	@Resource
	private RoomDao roomDao;
	@Resource
	private RoomFriendDao roomFriendDao;

	@Override
	public void sendApply(String targetUid, String msg,String fromUid) {
		UserApply applyEntity = getApplyEntity(targetUid, msg, fromUid);
		userApplyDao.save(applyEntity);
	}

	@Override
	@Transactional
	public List<GetApplyResp> getApply(String uid) {
		List<UserApply> apply = userApplyDao.getApply(uid);
		return apply.stream()
				.map(userApply -> {
					String applyId = userApply.getId().toString();
					String from = userApply.getUid().toString();
					String msg = userApply.getMsg();
					return GetApplyResp.builder()
							.fromUid(from)
							.msg(msg)
							.applyId(applyId)
							.build();
				}).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void solveApply(Integer decide, String applyId, String solverId) {
		UserApply applyById = userApplyDao.getById(applyId);
		if (Objects.isNull(applyById)){
			throw new BusinessException(PARAMS_ERROR,"还未申请");
		}
		if (!solverId.equals(applyById.getTargetUid().toString())){
			throw new BusinessException(PARAMS_ERROR,"你不能处理别人的好友请求");
		}
		if (decide == 1){
			// 创建好友
			Long uid = applyById.getUid();
			Long targetUid = applyById.getTargetUid();
			userFriendDao.save(getFriendEntity(uid,targetUid));
			userFriendDao.save(getFriendEntity(targetUid,uid));
			// 删除申请
			userApplyDao.removeById(applyById.getId());
			// 创建聊天室
			Room room = saveRoom(FRIEND.getType()); // 普通房间
			saveRoomFriend(room.getId(),uid,targetUid); // 好友房间
		}else {
			userApplyDao.removeById(applyById.getId());
		}
	}
	private RoomFriend saveRoomFriend(Long roomId,Long id1,Long id2){
		RoomFriend roomFriend = new RoomFriend();
		roomFriend.setRoomId(roomId);
		Long smallUid1 = id1>id2?id1:id2;
		Long bigUid2 = id1<id2?id1:id2;
		roomFriend.setUid1(smallUid1);
		roomFriend.setUid2(bigUid2);
		String roomKey = IdUtils.userIdToRoomKey(smallUid1, bigUid2);
		roomFriend.setRoomKey(roomKey);
		roomFriendDao.save(roomFriend);
		return roomFriend;
	}
	private Room saveRoom(Integer type){
		Room room = new Room();
		room.setType(type);
		roomDao.save(room);
		return room;
	}

	private UserApply getApplyEntity(String targetUid, String msg,String fromUid){
		return UserApply.builder()
				.uid(Long.parseLong(fromUid))
				.targetUid(Long.parseLong(targetUid))
				.msg(msg)
				.build();
	}
	private UserFriend getFriendEntity(Long uid,Long friendId){
		return UserFriend.builder()
				.friendUid(friendId)
				.uid(uid)
				.build();
	}
}
