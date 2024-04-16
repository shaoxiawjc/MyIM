package com.shaoxia.server.user.model.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wjc28
 * @version 1.0
 * @description: TODO
 * @date 2024-04-12 23:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResp implements Serializable {
	private String token;
}
