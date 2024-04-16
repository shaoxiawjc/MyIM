package com.shaoxia.server.user.controller;

import com.shaoxia.server.common.utils.JwtUtils;
import com.shaoxia.server.user.model.vo.BaseResponse;
import com.shaoxia.server.user.model.vo.ResultUtils;
import com.shaoxia.server.user.model.vo.friend.GetFriendResp;

import com.shaoxia.server.user.service.impl.FriendServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wjc28
 * @version 1.0
 * @description: TODO
 * @date 2024-04-13 18:57
 */
@RestController
@RequestMapping("/friend")
@Slf4j
public class FriendController {
	@Resource
	private FriendServiceImpl friendService;

	@GetMapping("/get")
	public BaseResponse<List<GetFriendResp>> getFriendList(HttpServletRequest request){
		String uid = JwtUtils.getUid(request);
		List<GetFriendResp> resp= friendService.getFriendList(uid);
		return ResultUtils.success(resp);
	}
}
