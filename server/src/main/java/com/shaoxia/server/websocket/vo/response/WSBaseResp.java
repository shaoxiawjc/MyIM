package com.shaoxia.server.websocket.vo.response;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson2.JSON;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wjc28
 * @version 1.0
 * @description: 基本响应类
 * @date 2024-04-14 14:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JSONType
public class WSBaseResp implements Serializable {
	private Integer code;

	public static TextWebSocketFrame success(WSBaseResp resp){
		return new TextWebSocketFrame(JSON.toJSONString(resp));
	}
}
