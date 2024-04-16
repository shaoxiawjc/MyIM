package com.shaoxia.server.websocket.model.domain;

import com.alibaba.fastjson.annotation.JSONType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;

/**
 * 消息表
 * @TableName message
 */
@TableName(value ="message")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JSONType
public class Message implements Serializable {
    /**
     * 消息id
     */
    @TableId(value = "id",type = ASSIGN_ID)
    private Long id;

    /**
     * 消息类型 1为文本类 2为图片类
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 房间id
     */
    @TableField(value = "room_id")
    private Long roomId;

    /**
     * 发送者uid
     */
    @TableField(value = "from_uid")
    private Long fromUid;

    /**
     * 消息内容
     */
    @TableField(value = "content")
    private String content;

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

    /**
     * 0代表未读，1代表已读
     */
    @TableField(value = "is_read")
    private Integer isRead;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}