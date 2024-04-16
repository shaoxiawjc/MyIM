package com.shaoxia.server.websocket.vo.request;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wjc28
 * @version 1.0
 * @description: TODO
 * @date 2024-04-15 9:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JSONType(typeName = "GroupChatReq")
public class GroupChatReq extends WSBaseReq implements Serializable {
	private Integer type;
	private String roomId;
	private String groupId;
	private String msg;
}
