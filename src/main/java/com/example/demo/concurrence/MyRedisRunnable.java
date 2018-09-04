package com.example.demo.concurrence;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

public class MyRedisRunnable implements Runnable{

	final String watchys = "watchKeys";

	Jedis jedis = new Jedis("127.0.0.1", 6379);

	String userInfo;

	public MyRedisRunnable() {
	}

	public MyRedisRunnable(String userInfo) {
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
				tx.decrBy(watchys,1);
				List<Object> list = tx.exec();

				if(list == null || list.size() == 0){
					String failUserInfo = "Fail : " + userInfo;
					String failInfo = "用户：" + failUserInfo + "抢购失败";
					System.out.println(failInfo);
					// 抢购失败业务逻辑
					jedis.setnx(failUserInfo,failInfo);
				}else {
					for (Object successObj : list) {
						String successUserInfo = "Success:" + successObj.toString() + userInfo;
						String successInfo = "用户：" +  successUserInfo + "抢购成功，成功人数：" + (1 - (valint - 100));
						System.out.println(successInfo);
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
