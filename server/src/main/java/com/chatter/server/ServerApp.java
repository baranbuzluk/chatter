package com.chatter.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerApp {

	private static List<ClientHandler> clientList = new ArrayList<>();

	public static void addClient(Socket socket) {

		ClientHandler clientHandler = new ClientHandler(socket, clientList);
		boolean start = clientHandler.start();
		if (start) {
			clientList.add(clientHandler);
		}
	}

	public static void main(String[] args) {
		try (ServerSocket serverSocket = new ServerSocket(9999)) {
			Socket accept;
			while ((accept = serverSocket.accept()) != null) {
				addClient(accept);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
