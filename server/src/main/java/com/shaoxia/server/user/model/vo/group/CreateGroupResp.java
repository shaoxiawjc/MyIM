package com.shaoxia.server.user.model.vo.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wjc28
 * @version 1.0
 * @description: 创建群聊响应类
 * @date 2024-04-15 8:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateGroupResp implements Serializable {
	private String groupName;
	private String groupRoomId;
	private String groupId;
}
