package com.shaoxia.server.user.model.vo.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wjc28
 * @version 1.0
 * @description: 获取群聊成员响应类
 * @date 2024-04-15 8:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetMembersResp implements Serializable {
	private String uid;
	private String role;
}
