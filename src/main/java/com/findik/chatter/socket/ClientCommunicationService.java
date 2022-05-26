package com.findik.chatter.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.core.ChatterConfiguration;
import com.findik.chatter.entity.Message;
import com.findik.chatter.xml.MessageXMLFileService;

@Component
public class ClientCommunicationService {

	private ChatterConfiguration chatterConfiguration;

	private MessageXMLFileService messageXMLFileService;

	private Socket socket;

	@Autowired
	public ClientCommunicationService(ChatterConfiguration chatterConfiguration,
			MessageXMLFileService messageXMLFileService) {
		this.messageXMLFileService = messageXMLFileService;
		this.chatterConfiguration = chatterConfiguration;
	}

	public synchronized boolean connect(String ip, int port) {
		if (socket == null) {
			socket = new Socket();
		}

		try {
			InetSocketAddress endpoint = new InetSocketAddress(ip, port);
			socket.connect(endpoint, chatterConfiguration.getConnectTimeout());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socket.isConnected();
	}

	public synchronized boolean disconnect() {
		boolean closed = false;
		try {
			if (socket != null) {
				socket.close();
				closed = socket.isClosed();
			}

			if (closed) {
				socket = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return closed;
	}

	public synchronized Message readMessage() {
		if (socket == null || !socket.isConnected()) {
			return null;
		}

		try {
			InputStream inputStream = socket.getInputStream();
			int available = inputStream.available();
			if (available > 0) {
				return messageXMLFileService.readFromXml(inputStream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public synchronized boolean writeMessage(Message message) {
		Message msg = Objects.requireNonNull(message, "Message can not be null!");
		if (socket == null || !socket.isConnected()) {
			return false;
		}
		try {
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(msg);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
