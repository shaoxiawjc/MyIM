package com.shaoxia.server.user.model.vo.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wjc28
 * @version 1.0
 * @description: 创建群聊请求实体类
 * @date 2024-04-15 8:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateGroupReq implements Serializable {
	private String groupName;
}
