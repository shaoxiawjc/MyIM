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
 * @date 2024-04-13 16:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolveApplyReq implements Serializable {
	private Integer decide;
	private String applyId;
}
