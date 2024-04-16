package com.shaoxia.server;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import lombok.Builder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author wjc28
 * @version 1.0
 * @description: TODO
 * @date 2024-04-08 23:33
 */
@SpringBootApplication(scanBasePackages = "com.shaoxia")
@MapperScan(value = {"com.shaoxia.server.user.mapper","com.shaoxia.server.websocket.mapper"})
public class MyApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class,args);
	}

	@Bean
	SnowflakeGenerator snowflakeGenerator(){
		return new SnowflakeGenerator();
	}
}

