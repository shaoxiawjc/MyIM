package com.shaoxia.server.websocket.vo.response;

import com.alibaba.fastjson.annotation.JSONType;
import com.shaoxia.server.websocket.vo.request.FriendChatReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wjc28
 * @version 1.0
 * @description: 好友聊天响应类
 * @date 2024-04-14 14:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JSONType
public class FriendChatResp extends WSBaseResp{
	private Integer type;
	private LocalDateTime time;
	private String msg;

	public static FriendChatResp ReqTOResp(FriendChatReq req){
		return FriendChatResp.builder()
				.msg(req.getMsg())
				.type(req.getType())
				.time(LocalDateTime.now())
				.build();
	}
}
