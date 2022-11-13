package com.chatter.connection;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.springframework.stereotype.Component;

@Component
public class CommunicationManagerImpl implements CommunicationManager {

	private static final int PORT = 9999;

	private List<String> activeHostAddressList = new ArrayList<>();

	private Map<String, Socket> connectedHostAddress = Collections.synchronizedMap(new HashMap<>());

	public CommunicationManagerImpl() {
		new Thread(() -> activeHostAddressList.addAll(IpAddressUtils.getActiveHostAddressesInLAN())).start();
		initServer();
	}

	private void initServer() {
		try {

			@SuppressWarnings("resource")
			final ServerSocket serverSocket = new ServerSocket(PORT);
			Thread thread = new Thread(() -> {

				while (true) {
					try {
						Socket accept = serverSocket.accept();
						String ipAddress = accept.getInetAddress().getHostAddress();
						connectedHostAddress.put(ipAddress, accept);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			});
			thread.setName("Socket Accept");
			thread.setDaemon(true);
			thread.start();

		} catch (Exception e) {
			String message = MessageFormat.format("Server could not be established! \n {0}", e);
			JOptionPane.showMessageDialog(new JFrame(), message, "ERROR OCCURRED", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

	}

	@Override
	public List<String> getActiveHostAddressList() {
		try {
			String localHostAddress = InetAddress.getLocalHost().getHostAddress();
			List<String> filteredList = activeHostAddressList.stream()
					.filter(host -> !host.equalsIgnoreCase(localHostAddress)).toList();
			return Collections.unmodifiableList(filteredList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override
	public CommunicationChannel connectToHostAddress(String ip) {
		try {
			Socket socket = connectedHostAddress.get(ip);
			if (socket == null) {
				socket = new Socket(ip, PORT);
				connectedHostAddress.put(ip, socket);
			}
			return new CommunicationChannel(socket.getInputStream(), socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
