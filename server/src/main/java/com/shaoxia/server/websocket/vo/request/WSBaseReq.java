package com.shaoxia.server.websocket.vo.request;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wjc28
 * @version 1.0
 * @description: 基本聊天请求类
 * @date 2024-04-08 23:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JSONType(seeAlso = {FriendChatReq.class, GroupChatReq.class})
public class WSBaseReq {
	private Integer code;
}
