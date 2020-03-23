package com.upstox.client;

import java.util.Scanner;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.upstox.config.TradeStompSessionHandler;

/**
 * Test client to connect and subscribe the live trading
 * @author arastogi
 */
public class WebsocketClient {

	 private static String URL = "ws://localhost:8080/upstox/websocket";
	 
	 public static void main(String[] args) {
	
		WebSocketClient client = new StandardWebSocketClient();
		WebSocketStompClient stompClient = new WebSocketStompClient(client);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		StompSessionHandler sessionHandler = new TradeStompSessionHandler();
		stompClient.connect(URL, sessionHandler);
		new Scanner(System.in).nextLine();
	}
}
