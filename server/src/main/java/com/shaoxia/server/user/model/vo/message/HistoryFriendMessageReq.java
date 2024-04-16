package com.shaoxia.server.user.model.vo.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wjc28
 * @version 1.0
 * @description: TODO
 * @date 2024-04-14 21:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryFriendMessageReq implements Serializable {
	private String roomId;
}
