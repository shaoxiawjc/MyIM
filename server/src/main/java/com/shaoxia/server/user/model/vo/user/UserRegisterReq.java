package com.shaoxia.server.user.model.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wjc28
 * @version 1.0
 * @description: 用户注册请求类
 * @date 2024-04-12 23:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterReq implements Serializable {
	private String name;
	private String phone;
	private String password;
	private String passwordAgain;
}
