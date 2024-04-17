package com.shaoxia.server;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import com.alibaba.fastjson2.JSON;
import com.shaoxia.server.websocket.dao.MessageDao;
import com.shaoxia.server.websocket.model.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.shaoxia.server.common.constant.RedisKey.JOB_FRIEND_LOCK;
import static com.shaoxia.server.common.constant.RedisKey.MESSAGE_LIST;

/**
 * @author wjc28
 * @version 1.0
 * @description: 测试类
 * @date 2024-04-16 12:44
 */

@SpringBootTest
@Slf4j
public class MyTest {
	@Resource
	private RedissonClient redissonClient;
	@Resource
	private MessageDao messageDao;
	@Resource
	private RedisTemplate redisTemplate;
	@Autowired
	private SnowflakeGenerator generator;

	@Test

	void test1(){
//		RLock lock = redissonClient.getLock(JOB_FRIEND_LOCK);
//		try {
//			// 只有一个线程能获取到锁
//			if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
//				System.out.println("getLock: " + Thread.currentThread().getId());
//				List<String> list =  redisTemplate.opsForList().range(MESSAGE_LIST,0,-1);
//				System.out.println(list);
//				List<Message> messages = list.stream().map(s -> {
//					Message message = JSON.parseObject(s, Message.class);
//					message.setId(generator.next());
//					return message;
//				}).collect(Collectors.toList());
//				messageDao.saveBatch(messages);
//				redisTemplate.delete(MESSAGE_LIST);
//			}
//		} catch (InterruptedException e) {
//			log.error("doCacheRecommendUser error", e);
//		} finally {
//			// 只能释放自己的锁
//			if (lock.isHeldByCurrentThread()) {
//				System.out.println("unLock: " + Thread.currentThread().getId());
//				lock.unlock();
//			}
//		}
	}
}
