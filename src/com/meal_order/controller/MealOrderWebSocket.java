package com.meal_order.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/MealOrderWebSocket")
public class MealOrderWebSocket {

	private javax.websocket.Session session = null;
//	private static final Set<Session> connectedSessions = Collections.synchronizedSet(new HashSet<>());
	private static CopyOnWriteArraySet<MealOrderWebSocket> webSocketSet = new CopyOnWriteArraySet<MealOrderWebSocket>();

	@OnOpen
	public void onOpen(Session session) throws IOException {
		this.session = session;
		webSocketSet.add(this);
//	connectedSessions.add(this);
	}

	@OnMessage
	public void onMessage(String jsonMap) {
		try {
			sendMessage(jsonMap);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		webSocketSet.remove(this);
	}

	public void sendMessage(String jsonMap) throws IOException {
		for (MealOrderWebSocket wbsc : webSocketSet) {
			wbsc.session.getAsyncRemote().sendText(jsonMap);
		}
	}

}
