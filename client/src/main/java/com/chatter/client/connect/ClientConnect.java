package com.chatter.client.connect;

import java.io.IOException;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.chatter.listener.api.EventInfo;
import com.chatter.listener.api.EventListener;

@Component
public class ClientConnect implements EventListener {

	private String ip;

	private int port;

	private DataClientSocket dataClientSocket;

	private CommunicationClientSocket communicationClientSocket;

	public ClientConnect() {
		this.ip = "127.0.0.1";
		this.port = 9999;
		dataClientSocket = new DataClientSocket(ip, port);
		communicationClientSocket = new CommunicationClientSocket(ip, port);
	}

	public void connect() throws IOException {
		if (!communicationClientSocket.isConnected()) {
			communicationClientSocket.connect();
		}
		byte[] started = new byte[10];
		Random random = new Random();
		random.nextBytes(started);
		communicationClientSocket.getOutputStream().write(started);
		communicationClientSocket.getOutputStream().flush();

	}

	@Override
	public void handleEvent(EventInfo eventInfo) {

	}

}
