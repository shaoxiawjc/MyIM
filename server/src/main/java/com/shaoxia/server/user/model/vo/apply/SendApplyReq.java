package com.shaoxia.server.user.model.vo.apply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wjc28
 * @version 1.0
 * @description: TODO
 * @date 2024-04-13 16:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendApplyReq implements Serializable {
	private String targetUid;
	private String msg;
}
