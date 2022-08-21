package com.chatter.server;

import java.net.Socket;
import java.util.List;

import com.chatter.socket.IOHandler;

public class ClientHandler extends IOHandler {

	private Socket socket;

	private List<ClientHandler> clients;

	public ClientHandler(Socket socket, List<ClientHandler> clients) {
		this.socket = socket;
		this.clients = clients;
	}

	public boolean start() {
		try {
			setInputStream(socket.getInputStream());
			setOutputStream(socket.getOutputStream());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected void receivedMessage(String message) {
		if (message.equals("STOP")) {
			clients.remove(this);
			System.out.println("Deleted client!");
		}
		sendMessageOtherClients(message);
	}

	private void sendMessageOtherClients(String message) {
		for (ClientHandler clientHandler : clients) {
			if (clientHandler == (this)) {
				continue;// if the client handler is itself, skip
			}
			clientHandler.sendMessage(message);
		}
	}

}
