package com.shaoxia.server.user.controller;

import com.shaoxia.server.common.exception.BusinessException;
import com.shaoxia.server.common.exception.ErrorCode;
import com.shaoxia.server.common.utils.JwtUtils;
import com.shaoxia.server.user.model.vo.BaseResponse;
import com.shaoxia.server.user.model.vo.ResultUtils;
import com.shaoxia.server.user.model.vo.message.HistoryFriendMessageReq;
import com.shaoxia.server.user.model.vo.message.HistoryFriendMessageResp;
import com.shaoxia.server.websocket.service.impl.MessageServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
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
 * @description: 聊天信息控制层
 * @date 2024-04-14 21:44
 */
@RestController
@RequestMapping("/message")
public class MessageController {
	@Resource
	private MessageServiceImpl messageService;

	/**
	 * 获取单聊未读的消息并标记为已读
	 * @param req
	 * @return
	 */
	@GetMapping("/getn")
	public BaseResponse<List<HistoryFriendMessageResp>> getNotReadMessage(
			HistoryFriendMessageReq req){
		String roomId = checkReq(req);
		List<HistoryFriendMessageResp> resp = messageService.getNotReadMessage(roomId);
		return ResultUtils.success(resp);
	}

	/**
	 * 获取单聊历史消息
	 * @param msgReq
	 * @return
	 */
	@GetMapping("/get")
	public BaseResponse<List<HistoryFriendMessageResp>> getHistoryFriendMsg(
			HistoryFriendMessageReq msgReq){
		String roomId = checkReq(msgReq);
		List<HistoryFriendMessageResp> resp = messageService.getHistoryFriendMsg(roomId);
		return ResultUtils.success(resp);
	}

	private String checkReq(HistoryFriendMessageReq msgReq){
		String roomId = "";
		if (Objects.isNull(msgReq)){
			throw new BusinessException(NULL_ERROR,"请求参数为空");
		}else {
			roomId = msgReq.getRoomId();
			if (StringUtils.isAnyBlank(roomId)){
				throw new BusinessException(NULL_ERROR,"请求参数为空");
			}
		}
		return roomId;
	}
}
