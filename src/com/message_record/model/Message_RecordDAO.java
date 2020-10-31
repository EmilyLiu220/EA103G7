package com.message_record.model;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Message_RecordDAO {
	// 此範例key的設計為(發送者名稱:接收者名稱)，實際應採用(發送者會員編號:接收者會員編號)

	private static JedisPool pool = JedisPoolUtil.getJedisPool(); // 類似取得 DataSource

	public static List<String> getHistoryMsg(String sender, String receiver) { // 取得歷史訊息
		String key = new StringBuilder(sender).append(":").append(receiver).toString();
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.auth("123456");
		List<String> historyData = jedis.lrange(key, 0, -1); // 從最左邊拿到最右邊的歷史紀錄
		jedis.close();
		return historyData; // 回傳歷史紀錄
	}

	public static void saveChatMessage(String sender, String receiver, String msgJson) { // 存入訊息
		// 對雙方來說，都要各存著歷史聊天記錄 (身分對調、各存一次)
		String senderKey = new StringBuilder(sender).append(":").append(receiver).toString();
		String receiverKey = new StringBuilder(receiver).append(":").append(sender).toString();
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		// 必須是右邊加入、先進先出，較新的訊息會最先出現
		jedis.rpush(senderKey, msgJson);
		jedis.rpush(receiverKey, msgJson);

		jedis.close();
	}

	public static void updMsgReadSts(String sender, String receiver) { // 取得歷史訊息
		String key = new StringBuilder(receiver).append(":").append(sender).toString();
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.auth("123456");
		
		List<String> allData = jedis.lrange(key, 0, -1); // 從最左邊拿到最右邊的歷史紀錄
		//更新讀取訊息狀態
		for(int i=0; i<allData.size(); i++) {
			String data = jedis.lindex(key, i);
			data = data.substring(0,data.length()-2) + "1}";
			jedis.lset(key, i, data);
		}
		jedis.close();
	}


	public static Set<String> getKeys() {
		Jedis jedis = null;
		jedis = pool.getResource();
		jedis.auth("123456");
		Set<String> allKey = jedis.keys("emp*");
		jedis.close();
		return allKey;
	}
	
}
