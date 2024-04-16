package com.shaoxia.server.user.controller;

import com.shaoxia.server.common.exception.BusinessException;
import com.shaoxia.server.common.exception.ErrorCode;
import com.shaoxia.server.common.utils.JwtUtils;
import com.shaoxia.server.user.model.vo.BaseResponse;
import com.shaoxia.server.user.model.vo.ResultUtils;
import com.shaoxia.server.user.model.vo.group.*;
import com.shaoxia.server.user.service.impl.GroupServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import static com.shaoxia.server.common.exception.ErrorCode.NULL_ERROR;

/**
 * @author wjc28
 * @version 1.0
 * @description: 群聊请求控制层
 * @date 2024-04-15 8:01
 */
@RestController
@RequestMapping("/group")
public class GroupController {
	@Resource
	private GroupServiceImpl groupService;

	/**
	 * 创建一个群聊
	 * @return
	 */
	@PutMapping("/create")
	public BaseResponse<CreateGroupResp> createGroup(
			CreateGroupReq createGroupReq
			,HttpServletRequest request){
		String groupName = "";
		if (Objects.isNull(createGroupReq)){
			throw new BusinessException(NULL_ERROR,"请求参数为空");
		}else {
			groupName = createGroupReq.getGroupName();
			if (StringUtils.isAnyBlank(groupName)){
				throw new BusinessException(NULL_ERROR,"请求参数为空");
			}
		}
		String uid = JwtUtils.getUid(request);
		CreateGroupResp resp = groupService.createGroup(groupName,uid);
		return ResultUtils.success(resp);
	}

	/**
	 * 查看群聊成员列表
	 */
	@GetMapping("/member/gets")
	public BaseResponse<List<GetMembersResp>> getMembers(GetMembersReq req){
		String groupId = "";
		if (Objects.isNull(req)){
			throw new BusinessException(NULL_ERROR,"请求参数为空");
		}else {
			groupId = req.getGroupId();
			if (groupId.isEmpty()){
				throw new BusinessException(NULL_ERROR,"请求参数为空");
			}
		}
		List<GetMembersResp> resp = groupService.getMembers(groupId);
		return ResultUtils.success(resp);
	}

	/**
	 * 加入一个群聊
	 */
	@PutMapping("/enter")
	public BaseResponse<Void> enterGroup(EnterGroupReq reqBody, HttpServletRequest request){
		String groupId = "";
		if (Objects.isNull(reqBody)){
			throw new BusinessException(NULL_ERROR,"请求参数为空");
		}else {
			groupId = reqBody.getGroupId();
			if (groupId.isEmpty()){
				throw new BusinessException(NULL_ERROR,"请求参数为空");
			}
		}
		String uid = JwtUtils.getUid(request);

		groupService.enterGroup(groupId,uid);
		return ResultUtils.success(null);
	}

	/**
	 * 设置权限
	 */
	@PutMapping("/role")
	public BaseResponse<Void> updateRole(UpdateRoleReq reqBody,HttpServletRequest req){
		String groupId = "";
		String updateRole = "";
		String updateUid = "";
		if (Objects.isNull(reqBody)){
			throw new BusinessException(NULL_ERROR,"请求参数为空");
		}else {
			groupId = reqBody.getGroupId();
			updateRole = reqBody.getUpdateRole();
			updateUid = reqBody.getUpdateUid();
			if (StringUtils.isAnyBlank(groupId,updateRole,updateUid)){
				throw new BusinessException(NULL_ERROR,"请求参数为空");
			}
		}
		String uid = JwtUtils.getUid(req);
		groupService.updateRole(groupId,updateRole,updateUid,uid);
		return ResultUtils.success(null);
	}

}
