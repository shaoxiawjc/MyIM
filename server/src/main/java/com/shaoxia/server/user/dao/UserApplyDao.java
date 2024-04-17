package com.shaoxia.server.user.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaoxia.server.user.mapper.UserApplyMapper;
import com.shaoxia.server.user.model.domain.UserApply;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wjc28
 * @version 1.0
 * @description: 用户好友申请持久化层
 * @date 2024-04-13 16:36
 */
@Component
public class UserApplyDao extends ServiceImpl<UserApplyMapper, UserApply> {
	@Resource
	private UserApplyMapper userApplyMapper;

	public List<UserApply> getApply(String uid){
		QueryWrapper<UserApply> wrapper = new QueryWrapper<>();
		wrapper.eq("target_uid",uid);
		return userApplyMapper.selectList(wrapper);
	}
}
