package com.shaoxia.server.user.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 群成员表
 * @TableName group_member
 */
@TableName(value ="group_member")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupMember implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 群组id
     */
    @TableField(value = "group_id")
    private Long groupId;

    /**
     * 成员uid
     */
    @TableField(value = "uid")
    private Long uid;

    /**
     * 成员角色 1群主 2管理员 3普通成员
     */
    @TableField(value = "role")
    private Integer role;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 逻辑删除
     */
    @TableField(value = "is_delete")
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}