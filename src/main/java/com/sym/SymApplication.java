package com.sym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @EnableRedisHttpSession : 기존 서버 세션 저장소를 사용하지 않고 Redis의 Session Stroage에 Session을 저장하게 해준다.
 */
@EnableRedisHttpSession
@SpringBootApplication
public class SymApplication{

	
	public static void main(String[] args) {
		SpringApplication.run(SymApplication.class, args);
	}


}
