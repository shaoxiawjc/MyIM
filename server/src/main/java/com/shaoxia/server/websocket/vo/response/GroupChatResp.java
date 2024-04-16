package com.shaoxia.server.websocket.vo.response;


import com.alibaba.fastjson.annotation.JSONType;
import com.shaoxia.server.websocket.model.domain.Message;
import com.shaoxia.server.websocket.vo.request.GroupChatReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wjc28
 * @version 1.0
 * @description: TODO
 * @date 2024-04-15 9:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JSONType
public class GroupChatResp extends WSBaseResp {
	private Integer type;
	private LocalDateTime time;
	private String msg;

	public static GroupChatResp ReqToResp(GroupChatReq req){
		return GroupChatResp.builder()
				.msg(req.getMsg())
				.time(LocalDateTime.now())
				.type(req.getType())
				.build();
	}

}
