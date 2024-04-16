package com.shaoxia.server.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shaoxia.server.user.model.domain.GroupMember;
import com.sun.tracing.dtrace.ModuleAttributes;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author wjc28
* @description 针对表【group_member(群成员表)】的数据库操作Mapper
* @createDate 2024-04-15 07:58:25
* @Entity generator.domain.GroupMember
*/
@Mapper
@Repository
public interface GroupMemberMapper extends BaseMapper<GroupMember> {

}




