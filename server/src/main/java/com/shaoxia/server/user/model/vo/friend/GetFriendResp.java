package com.shaoxia.server.user.model.vo.friend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wjc28
 * @version 1.0
 * @description: TODO
 * @date 2024-04-13 18:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetFriendResp implements Serializable{
	private String friendId;
	private String friendName;
	private String friendAvatar;
}
