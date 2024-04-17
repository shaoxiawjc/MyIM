package com.shaoxia.server.user.model.vo.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wjc28
 * @version 1.0
 * @description: 查看单聊历史消息响应类
 * @date 2024-04-14 21:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryFriendMessageResp implements Serializable {
	private String messageId;
	private String type;
	private String msg;
	private String fromUid;
}
