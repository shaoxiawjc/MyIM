package com.shaoxia.server.user.model.vo.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wjc28
 * @version 1.0
 * @description: TODO
 * @date 2024-04-15 8:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnterGroupReq implements Serializable {
	private String groupId;
}
