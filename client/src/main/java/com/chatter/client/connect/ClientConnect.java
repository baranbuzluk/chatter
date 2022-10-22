package com.chatter.client.connect;

import java.io.IOException;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.client.enums.ClientEvent;
import com.chatter.client.enums.ClientEventProperties;
import com.chatter.core.entity.Message;
import com.chatter.core.event.listener.ChatterEventListener;
import com.chatter.core.event.listener.EventInfo;
import com.chatter.core.event.listener.EventManager;
import com.chatter.core.io.ChatterIOHandler;
import com.chatter.core.io.ChatterIoListener;

@Component
public class ClientConnect implements ChatterEventListener, ChatterIoListener {

	private static final Logger LOG = LoggerFactory.getLogger(ClientConnect.class);

	private static final String IP = "127.0.0.1";

	private static final int PORT = 9999;

	private ChatterIOHandler ioHandler;

	private Socket socket;

	@Autowired
	public ClientConnect(EventManager eventManager) {
		eventManager.registerListener(this);
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ClientEvent.LOGGED_IN_ACCOUNT) {
			try {
				if (socket == null && ioHandler == null) {
					socket = new Socket(IP, PORT);
					ioHandler = new ChatterIOHandler(socket.getInputStream(), socket.getOutputStream());
					ioHandler.registerListener(this);
					LOG.info(MessageFormat.format("Connection to {0}  has been established.!", IP));

				} else {
					LOG.info("the connection cannot be established because 'socket' and 'ioHandler' are not null!");
				}
			} catch (IOException e) {
				LOG.info(MessageFormat.format("{0} could not be connected!", IP), e.getMessage());
			}
		} else if (eventInfo.getEvent() == ClientEvent.OUTGOING_MESSAGE) {
			Message message = (Message) eventInfo.get(ClientEventProperties.MESSAGE);
			if (ioHandler != null) {
				try {
					LOG.info("Message is being sended to server...");
					ioHandler.writeData(message);
					LOG.info("Message was sended to server!");
				} catch (IOException e) {
					LOG.info("While Being sended message, error occured!", e);
				}
			}
		}

	}

	@Override
	public void messageReceived(List<String> messages) {
		// TODO need to implement message parser
	}

}
