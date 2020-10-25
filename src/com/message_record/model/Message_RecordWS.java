package com.message_record.model;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@ServerEndpoint("/Message_RecordWS/{userName}")
public class Message_RecordWS { 

	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();

	@OnOpen 
	public void onOpen(@PathParam("userName") String userName, Session userSession) throws IOException {
		sessionsMap.put(userName, userSession); // 透過 Map 取得資料
		if("emp".equals(userName)) {
			Set<String> keys = Message_RecordDAO.getKeys(); // 列出所有的key
			Set<String> mems = new HashSet<String>();
			for(String key : keys) {
				String mem = key.substring(4);
				mems.add(mem);
			}
			State stateMessage = new State("open", userName, mems);
			String stateMessageJson = gson.toJson(stateMessage);
			userSession.getAsyncRemote().sendText(stateMessageJson); 
		}
		
	}

	@OnMessage	
	public void onMessage(Session userSession, String message) {
		Message_RecordVO msgVO = gson.fromJson(message, Message_RecordVO.class); // 取得傳來的 JSON 字串，用 gson 去指定類別並轉換回去
		String sender = msgVO.getSender(); // 取得發送者
		String receiver = msgVO.getReceiver(); // 取得接收者
		Message_RecordDAO.updMsgReadSts(sender, receiver);
		
		if ("history".equals(msgVO.getType())) { // 使用 List，因為訊息是有時間順序的(有序性)
			List<String> historyData = Message_RecordDAO.getHistoryMsg(sender, receiver); // 利用 JedisHandleMessage (DAO)去執行取得歷史訊息的動作
			String historyMsg = gson.toJson(historyData);  // 把歷史訊息(集合)轉成 Json 文字格式(因為要推到前端給 JS 處理)
			Message_RecordVO cmHistory = new Message_RecordVO("history", sender, receiver, historyMsg); // 用 msgVO VO 包裝資訊
			if (userSession != null && userSession.isOpen()) {
				userSession.getAsyncRemote().sendText(gson.toJson(cmHistory)); // 因為上面是用 VO (Java 物件)包裝 → 轉成 Json 後才會再輸出到前端
				System.out.println("history = " + gson.toJson(cmHistory));
			}
			
			Session receiverSession = sessionsMap.get(receiver); 
			if (receiverSession != null && receiverSession.isOpen()) {
				List<String> receiverHistoryData = Message_RecordDAO.getHistoryMsg(receiver, sender); 
				String receiverHistoryMsg = gson.toJson(receiverHistoryData); 
				Message_RecordVO mrHistory = new Message_RecordVO("history", sender, receiver, receiverHistoryMsg);
				receiverSession.getAsyncRemote().sendText(gson.toJson(mrHistory));
				System.out.println("history = " + gson.toJson(mrHistory));
			}
			return; // 只是為了拿到歷史聊天資訊 → 直接可以結束這個區塊，回到前端觸發 onmessage 事件
		}
		
		
		Session receiverSession = sessionsMap.get(receiver); // 已知接收者，當作 key 作為參數傳進取得連線資料
		if (receiverSession != null && receiverSession.isOpen()) {
			receiverSession.getAsyncRemote().sendText(message);
			message = message.substring(0,message.length()-2) + "1}";
			// 當兩個人在聊天，單個人都會有兩種身分，一為接收者一為發送者，但兩個人的聊天歷史訊息相同只是立足點不同
			// 故在 Redis 裡，把兩邊都各存一次，不需要再考慮正規化的問題，詳見 JedisHandleMessage.java 第 23 行
		}
		userSession.getAsyncRemote().sendText(message);
		Message_RecordDAO.saveChatMessage(sender, receiver, message); // 把發送者、接收者、訊息當作參數傳入並傳給 service 去存到 Redis 裡
		System.out.println("Message received: " + message);
	}
	
	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	@OnClose // 使用者視窗關閉
	public void onClose(Session userSession, CloseReason reason) {
		String userNameClose = null;
		Set<String> userNames = sessionsMap.keySet(); // 取得所有 users
		for (String userName : userNames) {
			if (sessionsMap.get(userName).equals(userSession)) { // 取得斷掉的連線
				userNameClose = userName;
				sessionsMap.remove(userName); // 移除這個連線
				break;
			}
		}
	}
}
