package com.chatter.socket;

import java.net.Socket;

public class ChatterSocket extends IOHandler {

	private Socket socket;

	private final MessageListener messageListener;

	public ChatterSocket(MessageListener messageListener) {
		this.messageListener = messageListener;
	}

	public synchronized boolean connect(String ip, int port) {
		if (socket != null) {
			return false;
		}

		try {
			socket = new Socket(ip, port);
			setOutputStream(socket.getOutputStream());
			setInputStream(socket.getInputStream());
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
			socket.shutdownInput();
			socket.shutdownOutput();
			socket.close();
			socket = null;
			return true;
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}

		return false;
	}

	public boolean isConnected() {
		return socket != null && socket.isConnected() && !socket.isClosed();
	}

	@Override
	protected void receivedMessage(String message) {
		messageListener.receivedMessage(message);
	}

}
