package com.shaoxia.server.common.constant;

import org.springframework.scheduling.annotation.Scheduled;

public interface RedisKey {
	String BASE = "shaoxia:my_im:";
	/**
	 * 用户token
	 */
	String TOKEN = BASE + "token:";

	/**
	 * 消息存储列表
	 */
	String MESSAGE_LIST = BASE + "message";

	/**
	 * 锁
	 */
	String JOB_FRIEND_LOCK = BASE + "job:friend:lock";
}
