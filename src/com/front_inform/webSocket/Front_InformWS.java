package com.front_inform.webSocket;

import java.io.IOException;
import java.util.Collection;
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

import com.front_inform.model.*;
import com.google.gson.Gson;


@ServerEndpoint("/FrontInformWS/{userName}") 
public class Front_InformWS {
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();
	
	
	@OnOpen
	public void onOpen(@PathParam("userName") String userName, Session userSession) throws IOException {
		sessionsMap.put(userName, userSession);
		// 取得所有使用者
		Set<String> userNames = sessionsMap.keySet(); // keySet() 可以把 Map 裡所有 key 的資料取出來，直接取得一個 Set 類別存放取出的資料
		
		// 把使用者名稱推到前端 ↓ → State 類別為額外寫的 VO，並非根據資料庫表格產生的，而是為了方便在程式操作期間、包裝資料用的Java物件
		State stateMessage = new State("open", userName, userNames); // 代表有人上線， chat 代表發出訊息， history 代表歷史聊天紀錄，close 代表離線
							// action, 使用者名稱, 其他在線上的使用者有誰
		String stateMessageJson = gson.toJson(stateMessage); // 用 gson 把上面的資料轉換成 Json 字串
		Collection<Session> sessions = sessionsMap.values(); // websocket 的連線
		for (Session session : sessions) {
			if (session.isOpen()) { // 如果是開啟中的
				session.getAsyncRemote().sendText(stateMessageJson); // 把動作、使用者、其他線上使用者的資訊都推到前端去
			}
		}

		String text = String.format("Session ID = %s, connected; userName = %s%nusers: %s", userSession.getId(), userName, userNames);
		System.out.println(text);
	}

	@OnMessage								// 前端傳來的 JSON 字串訊息
	public void onMessage(Session userSession, String message) {
		Front_InformVO chatMessage = gson.fromJson(message, Front_InformVO.class); // 取得一個 JSON 字串，用 gson 去指定類別並轉換回去
		
		String sender = chatMessage.getSender(); // 取得發送者
		String receiver = chatMessage.getReceiver(); // 取得接收者
		
		if ("history".equals(chatMessage.getType())) { // 使用 List，因為訊息是有時間順序的(有序性)
			List<String> historyData = JedisHandleMessage.getHistoryMsg(sender, receiver); // 利用 JedisHandleMessage (DAO)去執行取得歷史訊息的動作
			String historyMsg = gson.toJson(historyData); // 把歷史訊息轉成 Json 格式(因為要推到前端給 JS 處理)
			ChatMessage cmHistory = new ChatMessage("history", sender, receiver, historyMsg); // 用 ChatMessage VO 包裝資訊
			if (userSession != null && userSession.isOpen()) {
				userSession.getAsyncRemote().sendText(gson.toJson(cmHistory)); // 因為上面是用 VO (Java 物件)包裝 → 轉成 Json 後才會再輸出到前端
				System.out.println("history = " + gson.toJson(cmHistory));
				return; // 只是為了拿到歷史聊天資訊 → 直接可以結束這個區塊，回到前端觸發 onmessage 事件
			}
		}
		
		
		Session receiverSession = sessionsMap.get(receiver); // 已知接收者，當作 key 作為參數傳進取得連線資料
		if (receiverSession != null && receiverSession.isOpen()) {
			receiverSession.getAsyncRemote().sendText(message);
			userSession.getAsyncRemote().sendText(message);
			JedisHandleMessage.saveChatMessage(sender, receiver, message); // 把發送者、接收者、訊息當作參數傳入並傳給 service 去存到 Redis 裡
			// 當兩個人在聊天，單個人都會有兩種身分，一為接收者一為發送者，但兩個人的聊天歷史訊息相同只是立足點不同
			// 故在 Redis 裡，把兩邊都各存一次，不需要再考慮正規化的問題，詳見 JedisHandleMessage.java 第 23 行
		}
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

		if (userNameClose != null) { // 一樣會做列表刷新的動作
			State stateMessage = new State("close", userNameClose, userNames);
			String stateMessageJson = gson.toJson(stateMessage);
			Collection<Session> sessions = sessionsMap.values();
			for (Session session : sessions) {
				session.getAsyncRemote().sendText(stateMessageJson);
			}
		}
	
		String text = String.format("session ID = %s, disconnected; close code = %d%nusers: %s", userSession.getId(),
		reason.getCloseCode().getCode(), userNames);
		System.out.println(text);
	}
}
