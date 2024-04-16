package com.shaoxia.server.user.model.vo.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wjc28
 * @version 1.0
 * @description: 获取群聊成员请求类
 * @date 2024-04-15 8:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetMembersReq implements Serializable {
	private String groupId;
}
