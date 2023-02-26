package com.sym;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@SpringBootApplication
public class SymApplication implements CommandLineRunner {

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	public static void main(String[] args) {
		SpringApplication.run(SymApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try (RedisConnection connection = redisConnectionFactory.getConnection()) {
			String pong = connection.ping();
			if (!"PONG".equals(pong)) {
				throw new RuntimeException("Failed to connect to Redis");
			}
			System.out.println("Connected to Redis!");
		} catch (Exception e) {
			throw new RuntimeException("Failed to connect to Redis", e);
		}
	}
}
