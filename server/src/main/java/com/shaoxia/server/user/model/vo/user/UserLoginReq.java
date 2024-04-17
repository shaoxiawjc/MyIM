package com.shaoxia.server.user.model.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wjc28
 * @version 1.0
 * @description: 用户登录请求类
 * @date 2024-04-12 23:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginReq {
	private String phone;
	private String password;
}
