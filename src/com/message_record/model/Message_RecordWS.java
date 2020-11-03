package com.message_record.model;

import java.io.IOException;
import java.util.ArrayList;
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

import org.json.JSONObject;

import com.google.gson.Gson;

@ServerEndpoint("/Message_RecordWS/{userName}")
public class Message_RecordWS {

	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("userName") String userName, Session userSession) throws IOException {
		sessionsMap.put(userName, userSession); // 透過 Map 取得資料

		// 若為員工，取出所有與會員聊天紀錄並刷新
		if ("emp".equals(userName)) {
			Set<String> keys = Message_RecordDAO.getKeys(); // 列出所有的key
			List<String> msgInfoList = new ArrayList<String>();
			for (String key : keys) {
				// 計算未讀比數與最後留言時間
				int count = 0;
				String latestMsgTime = "";
				List<String> AllMsg= Message_RecordDAO.getHistoryMsg(key.substring(4),"emp");
				for(String msg: AllMsg) {
					JSONObject msgData = new JSONObject(msg);
					if("emp".equals(msgData.get("receiver"))) {
						if((Integer) msgData.get("readSts") == 0) {
							count++;
							latestMsgTime = (String) msgData.get("timestamp");
						}
					}
				}
				JSONObject msgJson = new JSONObject();
				msgJson.put("mem", key.substring(4));
				msgJson.put("unread", count);
				msgJson.put("latestMsgTime", latestMsgTime);
				msgInfoList.add(msgJson.toString());
			}
			// 對前端發送 open 訊息，將畫面左側聊天清單刷新
			State stateMessage = new State("refresh", msgInfoList);
			String stateMessageJson = gson.toJson(stateMessage);
			userSession.getAsyncRemote().sendText(stateMessageJson);
		}

	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		Message_RecordVO msgVO = gson.fromJson(message, Message_RecordVO.class); // 取得傳來的 JSON 字串，用 gson 去指定類別並轉換回去
		String sender = msgVO.getSender(); // 取得發送者
		String receiver = msgVO.getReceiver(); // 取得接收者
		Session receiverSession = sessionsMap.get(receiver); //接收者Session
		
		// 收到前端 history 訊息，取出聊天紀錄並刷新
		if ("history".equals(msgVO.getType())) { // 使用 List，因為訊息是有時間順序的(有序性)
			List<String> historyData = Message_RecordDAO.getHistoryMsg(sender, receiver); // 利用 JedisHandleMessage
																							// (DAO)去執行取得歷史訊息的動作
			String historyMsg = gson.toJson(historyData); // 把歷史訊息(集合)轉成 Json 文字格式(因為要推到前端給 JS 處理)
			Message_RecordVO cmHistory = new Message_RecordVO("history", sender, receiver, historyMsg); // 用 msgVO VO

			// 包裝資訊，對前端發送 history 訊息，刷新發送者頁面
			if (userSession != null && userSession.isOpen()) {
				userSession.getAsyncRemote().sendText(gson.toJson(cmHistory)); // 因為上面是用 VO (Java 物件)包裝 → 轉成 Json 後才會再輸出到前端
				System.out.println(sender + " history = " + gson.toJson(cmHistory));
			}
		}


		// 收到前端 chat 訊息，將訊息轉發
		if("chat".equals(msgVO.getType())) {
			// 若接收者在線，將訊息傳回前端處理
			if (receiverSession != null && receiverSession.isOpen()) {
				receiverSession.getAsyncRemote().sendText(message);
				System.out.println(receiver + " Message received: " + message);
			}
			// 發送者也將訊息傳回自己的前端處理
			userSession.getAsyncRemote().sendText(message);
			Message_RecordDAO.saveChatMessage(sender, receiver, message); // 把發送者、接收者、訊息當作參數傳入並傳給 service 去存到 Redis 裡
			System.out.println(sender + " Message received: " + message);
		} else { // 若為 history 或 read 情況
			// 若接收者在線，對前端發送 read 訊息，刷新已讀未讀狀態
			if (receiverSession != null && receiverSession.isOpen()) {
				Message_RecordVO updReadSts = new Message_RecordVO("read", sender, receiver, "");
				receiverSession.getAsyncRemote().sendText(gson.toJson(updReadSts));
				System.out.println("receiver check readSts  = " + gson.toJson(updReadSts));
			}
		}
		
		
		Message_RecordDAO.updMsgReadSts(sender, receiver); // 更新接收者過去訊息的已讀狀態
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
