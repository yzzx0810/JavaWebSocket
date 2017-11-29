package com.usst.zhangxin.controller;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
public class WebSocketTest {
	//当前在线人数
	private static int onlineCount = 0;
	
	private static CopyOnWriteArraySet<WebSocketTest> webSocketSet = new CopyOnWriteArraySet<>();
	
	private Session session = null;
	
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		webSocketSet.add(this);
		addOnlineCount();
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	}
	
	@OnClose
	public void onClose() {
		webSocketSet.remove(this);
		subOnlineCount();
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		for(WebSocketTest item : webSocketSet) {
			try {
				item.sendMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	public static synchronized void addOnlineCount() {
		WebSocketTest.onlineCount++;
	}
	
	public static synchronized void subOnlineCount() {
		WebSocketTest.onlineCount--;
	}
	
	public static synchronized int getOnlineCount() {
		return WebSocketTest.onlineCount;
	}
	
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}
}
