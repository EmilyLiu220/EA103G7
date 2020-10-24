package com.meal_order.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

//	private javax.websocket.Session session = null;
//	private static final Set<Session> connectedSessions = Collections.synchronizedSet(new HashSet<>());
//	
//	@OnOpen
//	public void onOpen(Session session) throws IOException {
////	this.session=session;
////	connectedSessions.add(session);
//	}
//
//	@OnMessage
//		public void onMessage() {
////		try {
////			sendMessage();
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
////		
//		
//	}
//
//	@OnClose
//	public void onClose(Session userSession, CloseReason reason) {
//	}
//	
////	public void sendMessage() throws IOException{
////        //群發訊息
////        for(Session item: connectedSessions){
////            item.getBasicRemote().sendText("reload");
////        }
////    }

}
