package com.chatter.client.connect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.client.enums.ClientEvent;
import com.chatter.client.enums.ClientEventProperties;
import com.chatter.core.socket.ChatterSocket;
import com.chatter.core.socket.MessageListener;
import com.chatter.data.entity.Message;
import com.chatter.data.util.MessageUtil;
import com.chatter.listener.api.EventInfo;
import com.chatter.listener.api.ChatterEventListener;
import com.chatter.listener.api.EventManager;

@Component
public class ClientConnect implements ChatterEventListener, MessageListener {

	private static final String IP = "127.0.0.1";

	private static final int PORT = 9999;

	private ChatterSocket socket;

	private EventManager eventManager;

	@Autowired
	public ClientConnect(EventManager eventManager) {
		this.eventManager = eventManager;
		eventManager.registerListener(this);
		socket = new ChatterSocket(this);
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ClientEvent.LOGGED_IN_ACCOUNT) {
			connectToServer();
		} else if (eventInfo.getEvent() == ClientEvent.OUTGOING_MESSAGE) {
			Message message = (Message) eventInfo.get(ClientEventProperties.MESSAGE);
			String parseMessage = MessageUtil.parseMessage(message);
			socket.sendMessage(parseMessage);
		}

	}

	private void connectToServer() {
		socket.disconnect();
		socket.connect(IP, PORT);
	}

	@Override
	public void receivedMessage(String message) {
		Message incomingMessage = MessageUtil.convertToMessage(message);
		if (incomingMessage != null) {
			EventInfo event = new EventInfo(ClientEvent.INCOMING_MESSAGE);
			event.put(ClientEventProperties.MESSAGE, incomingMessage);
			eventManager.sendEvent(event);
		}
	}

}
