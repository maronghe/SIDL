package com.example.demo.concurrence;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyRedisRunnable implements Runnable{

	final String watchys = "watchKeys";
	Jedis jedis = new Jedis("127.0.0.1", 6379);
	int i ;
	
	String userInfo;

	public MyRedisRunnable() {
	}

	public MyRedisRunnable(String userInfo,int i) {
		this.i = i;
		this.userInfo = userInfo;
	}
	
	@Override
	public void run() {
		try {
			
			jedis.watch(watchys);
			String val = jedis.get(watchys);
			int valint = Integer.valueOf(val);
			if (valint <= 100 && valint >= 1){
				Transaction tx = jedis.multi(); // 开启事务
				tx.incrBy(watchys,-1);
				List<Object> list = tx.exec();
				
				if(list == null || list.size() == 0){
					String failUserInfo = "Fail : " + userInfo;
					String failInfo = "用户：" + failUserInfo + "抢购失败," + (100 - valint);
					System.out.println("People :" + i);
					System.out.println(failInfo + "--->" + i);
					// 抢购失败业务逻辑
					jedis.setnx(failUserInfo,failInfo);
				}else {
					for (Object successObj : list) {
						String successUserInfo = "Success:" + successObj.toString() + "-" + userInfo;
						System.out.println("People :" + i);
						String successInfo = "用户：" +  successUserInfo + "抢购成功，成功人数：" + (1 - (valint - 100));
						System.out.println(successInfo + "--->" + i);
						jedis.setnx(successUserInfo, userInfo);
					}
				}
			}else {
				String failUserInfo = "kcfail" + userInfo;
				String failInfo = "用户:"+ failUserInfo +"抢购失败, 库存不足";
				System.out.println(failInfo);
				jedis.setnx(failUserInfo, failInfo);
				return;
			}

		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
}
