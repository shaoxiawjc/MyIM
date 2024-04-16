package com.shaoxia.server.user.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxia.server.common.utils.PasswordUtil;
import com.shaoxia.server.user.mapper.UserMapper;
import com.shaoxia.server.user.model.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserDao extends ServiceImpl<UserMapper, User> {

	public User getUserByPhone(String phone){
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.eq("phone",phone);
		User user = getOne(wrapper);
		return user;
	}

	public void register(String name,String password,String phone){
		String storePassword = PasswordUtil.hashPassword(password);
		User build = User.builder()
				.name(name)
				.password(storePassword)
				.phone(phone)
				.build();
		save(build);
	}

}