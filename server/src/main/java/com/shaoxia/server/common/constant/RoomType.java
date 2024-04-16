package com.shaoxia.server.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoomType {
	FRIEND(1),
	GROUP(2)
	;
	private Integer type;
}
