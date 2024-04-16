package com.shaoxia.server.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WSReqCode {
	/**
	 * 请求单聊
	 */
	FRIEND_CHAT(1),
	/**
	 * 请求群聊
	 */
	GROUP_CHAT(2),
	/**
	 * 不存在的消息码
	 */
	ERROR(-1)
	;
	private final Integer code;

	public static WSReqCode match(Integer code){
		for (WSReqCode commandType:WSReqCode.values()){
			if (commandType.getCode().equals(code)){
				return commandType;
			}
		}
		return ERROR;
	}

}
