/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chatter;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.chatter.server.ClientHandler;

public class MainClass {

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