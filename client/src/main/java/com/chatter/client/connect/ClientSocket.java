package com.chatter.client.connect;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public abstract class ClientSocket {

	private String ip;

	private int port;

	private Socket socket;

	protected ClientSocket(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public void connect() throws IOException {
		if (socket == null) {
			socket = new Socket();
		}
		InetSocketAddress endpoint = new InetSocketAddress(ip, port);
		socket.connect(endpoint, 1000);
	}

	public void disconnect() throws IOException {
		if (socket == null || !isConnected())
			return;

		socket.shutdownInput();
		socket.getInputStream().close();
		socket.shutdownOutput();
		socket.getOutputStream().close();
		socket.close();
		socket = null;
	}

	public boolean isConnected() {
		return socket != null && socket.isConnected();
	}

	public InputStream getInputStream() throws IOException {
		if (isConnected() && !socket.isInputShutdown())
			return socket.getInputStream();
		return null;
	}

	public OutputStream getOutputStream() throws IOException {
		if (isConnected() && !socket.isOutputShutdown())
			return socket.getOutputStream();
		return null;
	}

}
