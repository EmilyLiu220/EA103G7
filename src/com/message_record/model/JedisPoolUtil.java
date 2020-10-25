package com.message_record.model;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtil {
	private static JedisPool pool = null; // 建立空值的池子

	private JedisPoolUtil() {
	}
	 				// 回傳池子
	public static JedisPool getJedisPool() {
		if (pool == null) { // 如果池子還不存在
			synchronized (JedisPoolUtil.class) { // 必須先做鎖定動作 (若剛好有很多執行緒進入，但其實池子只需要一個就夠了)
				if (pool == null) { // 進來後，因為池子的建立只要一次就好，故再一次判斷，若為 null 才會建立
					JedisPoolConfig config = new JedisPoolConfig();
					// 類似在 tomcat server 的 context.xml 是一樣的
					config.setMaxTotal(8); // 池子最大連線數量
					config.setMaxIdle(8); // 最大閒置連線數
					config.setMaxWaitMillis(10000); // 若連線沒有了，最大的延遲時間 (等待十秒)
					pool = new JedisPool(config, "localhost", 6379); // 初始化連線池(類似 tomcat 裡的 context.xml 設定)
				}
			}
		}
		return pool;
	}

	// 可在ServletContextListener的contextDestroyed裡呼叫此方法註銷Redis連線池
	public static void shutdownJedisPool() {
		if (pool != null)
			pool.destroy();
	}
}
