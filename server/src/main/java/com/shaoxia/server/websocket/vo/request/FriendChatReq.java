package com.shaoxia.server.websocket.vo.request;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wjc28
 * @version 1.0
 * @description: 好友聊天请求类
 * @date 2024-04-14 14:07
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JSONType(typeName = "FriendChatReq")
public class FriendChatReq extends WSBaseReq{
	private Integer type;
	private String targetId;
	private String roomId;
	private String msg;
}
