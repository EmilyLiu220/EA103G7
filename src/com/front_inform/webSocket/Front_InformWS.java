package com.front_inform.webSocket;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.front_inform.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@ServerEndpoint("/Front_InformWS/{userName}") 
public class Front_InformWS {
	
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();
	
	
	@OnOpen
	public void onOpen(@PathParam("userName") String userName, Session userSession) throws IOException {
		sessionsMap.put(userName, userSession); // 把上線者存入 Map 中
		Set<String> userNames = sessionsMap.keySet(); // 會是一個含有許多 mem_no 的 Set
		Front_InformService fiSvc = new Front_InformService();
		Gson gsonReceiver = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		for(String mem_no : userNames) { // 走訪每個在線上的會員
			Session memSession = sessionsMap.get(mem_no); // 取得 userSession
			if(memSession!=null && memSession.isOpen()) { // 確認此 session 是開啟的
				List<Front_InformVO> fiVOs = fiSvc.getMyInform(mem_no); // 取得該會員的所有通知訊息
				// 因為上面是直接傳來 VO → 轉成 Json 後才會再輸出到前端
				userSession.getAsyncRemote().sendText(gsonReceiver.toJson(fiVOs)); // 傳出String
				System.out.println("My History Inform : " + gsonReceiver.toJson(fiVOs));
			}
		}
	}

	public void onMessage(List<Front_InformVO> fiVOs) { // DB 傳來的物件
		Gson gsonReceiver = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		for(Front_InformVO fiVO : fiVOs) {
			String mem_no = fiVO.getMem_no();
			if(sessionsMap.containsKey(mem_no)) {
				Session userSession = sessionsMap.get(mem_no); // 取得 userSession
				if(userSession != null && userSession.isOpen()) {
					userSession.getAsyncRemote().sendText(gsonReceiver.toJson(fiVO));
					System.out.println("new Inform : " + gsonReceiver.toJson(fiVO));
				}
			}
		}
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	@OnClose // 使用者視窗關閉
	public void onClose(Session userSession, CloseReason reason) {
		Set<String> userNames = sessionsMap.keySet(); // 取得所有 users
		for (String userName : userNames) {
			if (sessionsMap.get(userName).equals(userSession)) { // 取得斷掉的連線
				sessionsMap.remove(userName); // 移除這個連線
				break;
			}
		}
	}
}
