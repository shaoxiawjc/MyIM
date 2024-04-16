package com.shaoxia.server.user.model.vo.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wjc28
 * @version 1.0
 * @description: 修改群聊成员权限
 * @date 2024-04-15 8:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRoleReq implements Serializable {
	private String groupId;
	private String updateUid;
	private String updateRole;
}
