package com.shaoxia.server.user.controller;

import com.shaoxia.server.common.exception.BusinessException;
import com.shaoxia.server.user.model.vo.BaseResponse;
import com.shaoxia.server.user.model.vo.ResultUtils;
import com.shaoxia.server.user.model.vo.user.UserLoginReq;
import com.shaoxia.server.user.model.vo.user.UserLoginResp;
import com.shaoxia.server.user.model.vo.user.UserRegisterReq;
import com.shaoxia.server.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.Objects;

import static com.shaoxia.server.common.exception.ErrorCode.PARAMS_ERROR;

/**
 * @author wjc28
 * @version 1.0
 * @description: 用户模块控制层
 * @date 2024-04-12 23:08
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserServiceImpl userService;


	@PostMapping("/login")
	public BaseResponse<UserLoginResp> login(UserLoginReq userLoginReq){
		if (Objects.isNull(userLoginReq)){
			throw new BusinessException(PARAMS_ERROR,"传入对象为空");
		}else {
			if (StringUtils.isAnyBlank(userLoginReq.getPhone(),userLoginReq.getPassword())){
				throw new BusinessException(PARAMS_ERROR,"参数值为空");
			}
		}
		UserLoginResp success = userService.login(userLoginReq);

		return ResultUtils.success(success);
	}


	@PostMapping(value = "/register")
	public BaseResponse<Void> register(UserRegisterReq userRegisterReq){
		if (Objects.isNull(userRegisterReq)){
			throw new BusinessException(PARAMS_ERROR,"传入对象为空");
		}else {
			if (StringUtils.isAnyBlank(userRegisterReq.getPhone(),
					userRegisterReq.getPassword(),
					userRegisterReq.getName(),
					userRegisterReq.getPasswordAgain())){
				throw new BusinessException(PARAMS_ERROR,"参数值为空");
			}
		}

		userService.register(userRegisterReq);

		return ResultUtils.success(null);
	}
}

