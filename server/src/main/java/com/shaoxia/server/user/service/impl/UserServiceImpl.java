package com.shaoxia.server.user.service.impl;

import com.shaoxia.server.common.exception.BusinessException;
import com.shaoxia.server.common.exception.ErrorCode;
import com.shaoxia.server.common.utils.JwtUtils;
import com.shaoxia.server.common.utils.PasswordUtil;
import com.shaoxia.server.user.dao.UserDao;
import com.shaoxia.server.user.model.domain.User;
import com.shaoxia.server.user.model.vo.user.UserLoginReq;
import com.shaoxia.server.user.model.vo.user.UserLoginResp;
import com.shaoxia.server.user.model.vo.user.UserRegisterReq;
import com.shaoxia.server.user.service.UserService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.shaoxia.server.common.constant.RedisKey.TOKEN;
import static com.shaoxia.server.common.exception.ErrorCode.PARAMS_ERROR;

/**
 * @author wjc28
 * @version 1.0
 * @description: TODO
 * @date 2024-04-12 23:09
 */
@Service
public class UserServiceImpl implements UserService {
	@Resource
	private UserDao userDao;
	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public UserLoginResp login(UserLoginReq userLoginReq) {
		String phone = userLoginReq.getPhone();
		String inputPassword = userLoginReq.getPassword();

		User userByPhone = userDao.getUserByPhone(phone);
		if (Objects.isNull(userByPhone)){
			throw new BusinessException(ErrorCode.NULL_ERROR,"电话不存在");
		}

		String password = PasswordUtil.hashPassword(inputPassword);

		if (PasswordUtil.verifyPassword(password,userByPhone.getPassword())){
			String token = createToken(userByPhone.getId().toString());

			redisTemplate.opsForValue().set(TOKEN+userByPhone.getId().toString(),token,1, TimeUnit.DAYS);

			UserLoginResp resp = new UserLoginResp(token);
			return resp;
		}else {
			throw new BusinessException(PARAMS_ERROR,"密码错误");
		}
	}

	@Override
	public void register(UserRegisterReq userRegisterReq) {
		String name = userRegisterReq.getName();
		String phone = userRegisterReq.getPhone();
		String password = userRegisterReq.getPassword();
		String passwordAgain = userRegisterReq.getPasswordAgain();

		if (!passwordAgain.equals(password)){
			throw new BusinessException(PARAMS_ERROR,"俩次输入密码不一致");
		}

		User userByPhone = userDao.getUserByPhone(phone);
		if (Objects.nonNull(userByPhone)){
			throw new BusinessException(PARAMS_ERROR,"此电话号码已被注册");
		}

		userDao.register(name,password,phone);
	}


	private String createToken(String id){
		HashMap<String, String> map = new HashMap<>();
		map.put("id",id);
		String token = JwtUtils.getToken(map);
		return token;
	}


}
