package com.example.demo.concurrence;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * My Redis test
 * @author Logan
 */
public class MyRedistest {

	public static void main(String[] args) {

		final String watchKeys = "watchKeys";

		// 创建20个线程
		ExecutorService executorService = Executors.newFixedThreadPool(40); // 40个线程并发执行

		// 创建jedis对象
		Jedis jedis = new Jedis("127.0.0.1", 6379,100000);
		System.out.println(jedis.ping());
		jedis.set(watchKeys, "100");// 起始库存100
		System.out.println(jedis.bgsave()); // save in background
		jedis.close();

		// 1500人并发抢购
		for (int i = 0; i < 1500; i++) {
			executorService.execute(new MyRedisRunnable("user<" + getRandomString(6) + ">", i));
		}
		// shut down thread pool
		executorService.shutdown();
//		executorService.awaitTermination(60, TimeUnit.SECONDS);
	}
	public static String getRandomString(int length) { //length是随机字符串长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

}
