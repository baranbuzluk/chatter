package com.chatter.core.socket;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatterSocket {

	private Socket socket;

	private PrintWriter printWriter;

	private final MessageListener messageListener;

	private volatile boolean runLoop;

	public ChatterSocket(MessageListener messageListener) {
		this.messageListener = messageListener;
	}

	public synchronized boolean connect(String ip, int port) {
		if (socket != null) {
			return false;
		}

		try {
			socket = new Socket(ip, port);
			printWriter = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
			runLoop = true;
			Thread thread = new Thread(new RunLoop(), "Message Listener-Thread");
			thread.setDaemon(true);
			thread.start();
			return true;
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}

		return false;
	}

	public synchronized boolean disconnect() {
		if (socket == null) {
			return false;
		}

		try {
			runLoop = false;
			socket.shutdownInput();
			socket.shutdownOutput();
			socket.close();

			socket = null;
			printWriter = null;
			return true;
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}

		return false;
	}

	public boolean isConnected() {
		return socket != null && socket.isConnected() && !socket.isClosed();
	}

	public boolean sendMessage(String message) {
		if (printWriter != null) {
			printWriter.println(message);
			return true;
		}
		return false;
	}

	class RunLoop implements Runnable {
		@Override
		public void run() {
			if (socket == null) {
				return;
			}
			try {
				InputStream inputStream = socket.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
				while (runLoop) {
					String readLine;
					if ((readLine = reader.readLine()) != null) {
						messageListener.receivedMessage(readLine);
					}
				}
				reader.close();
				inputStream.close();
			} catch (Exception e) {
				System.err.println(e.getLocalizedMessage());
			}
		}
	}

}
