package com.shaoxia.server.job;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.alibaba.fastjson2.JSON;
import com.shaoxia.server.websocket.dao.MessageDao;
import com.shaoxia.server.websocket.model.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.shaoxia.server.common.constant.RedisKey.*;

/**
 * @author wjc28
 * @version 1.0
 * @description: 定时持久话消息
 * @date 2024-04-14 22:29
 */
@Component
@Slf4j
public class SaveMessage {
	@Resource
	private RedissonClient redissonClient;
	@Resource
	private MessageDao messageDao;
	@Resource
	private RedisTemplate redisTemplate;
	@Autowired
	private SnowflakeGenerator generator;

	/**
	 * 每小时存储一次消息
	 */
	@Scheduled(cron = "0 0 * * * *")
	public void saveMessage(){

		RLock lock = redissonClient.getLock(JOB_FRIEND_LOCK);
		try {
			// 只有一个线程能获取到锁
			if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
				System.out.println("getLock: " + Thread.currentThread().getId());
				List<String> list =  redisTemplate.opsForList().range(MESSAGE_LIST,0,-1);
				System.out.println(list);
				List<Message> messages = list.stream().map(s -> {
					Message message = JSON.parseObject(s, Message.class);
					message.setId(generator.next());
					return message;
				}).collect(Collectors.toList());
				messageDao.saveBatch(messages);
				redisTemplate.delete(MESSAGE_LIST);
			}
		} catch (InterruptedException e) {
			log.error("doCacheRecommendUser error", e);
		} finally {
			// 只能释放自己的锁
			if (lock.isHeldByCurrentThread()) {
				System.out.println("unLock: " + Thread.currentThread().getId());
				lock.unlock();
			}
		}
	}
}
