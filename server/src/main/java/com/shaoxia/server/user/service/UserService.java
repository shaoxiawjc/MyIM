package com.shaoxia.server.user.service;


import com.shaoxia.server.user.model.vo.user.UserLoginReq;
import com.shaoxia.server.user.model.vo.user.UserLoginResp;
import com.shaoxia.server.user.model.vo.user.UserRegisterReq;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
	UserLoginResp login(UserLoginReq userLoginReq);

	void register(UserRegisterReq userRegisterReq);

}
