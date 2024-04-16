package com.shaoxia.server.user.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxia.server.user.mapper.GroupMemberMapper;
import com.shaoxia.server.user.model.domain.GroupMember;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wjc28
 * @version 1.0
 * @description: 群聊成员持久化层
 * @date 2024-04-15 8:08
 */
@Component
public class GroupMemberDao extends ServiceImpl<GroupMemberMapper, GroupMember> {
	@Resource
	private GroupMemberMapper groupMemberMapper;

	public void updateRole(GroupMember member){
		UpdateWrapper<GroupMember> wrapper = new UpdateWrapper<>();
		wrapper.eq("group_id",member.getGroupId())
				.eq("uid",member.getUid());
		groupMemberMapper.update(member,wrapper);
	}

	public List<GroupMember> getMembersByGroupId(String groupId){
		QueryWrapper<GroupMember> wrapper = new QueryWrapper<>();
		wrapper.eq("group_id",groupId);
		return groupMemberMapper.selectList(wrapper);
	}

	public List<Long> getMemberUid(String groupId){
		return getMembersByGroupId(groupId).stream().map(
				GroupMember::getUid
		).collect(Collectors.toList());
	}

	public GroupMember getByGroupIdAndUid(String groupId,String uid){
		QueryWrapper<GroupMember> wrapper = new QueryWrapper<>();
		wrapper.eq("group_id",groupId)
				.eq("uid",uid);
		return groupMemberMapper.selectOne(wrapper);
	}


	public void add(String groupId,String uid){
		GroupMember build = GroupMember.builder()
				.groupId(Long.parseLong(groupId))
				.uid(Long.parseLong(uid))
				.role(3)
				.build();
		save(build);
	}
}
