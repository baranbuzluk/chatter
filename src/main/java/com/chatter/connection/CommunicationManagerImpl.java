package com.chatter.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.springframework.stereotype.Component;

import com.chatter.data.entity.Message;

@Component
public class CommunicationManagerImpl implements CommunicationManager {

	private static final int PORT = 9999;

	private List<String> activeHostAddressList = new ArrayList<>();

	private Map<String, CommunicationChannel> connectedHostAddress = Collections.synchronizedMap(new HashMap<>());

	public CommunicationManagerImpl() {
		new Thread(() -> activeHostAddressList.addAll(IpAddressUtils.getActiveHostAddressesInLAN())).start();
		initServer();

		initCommunicationChannelListener();
	}

	private void initCommunicationChannelListener() {
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
			for (CommunicationChannel communicationChannel : connectedHostAddress.values()) {
				List<String> message = communicationChannel.getMessage();
				System.err.println(message);
			}

		}, 0, 500, TimeUnit.MILLISECONDS);

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
						if (connectedHostAddress.get(ipAddress) == null) {
							CommunicationChannel communicationChannel = new CommunicationChannel(
									accept.getInputStream(), accept.getOutputStream());
							connectedHostAddress.put(ipAddress, communicationChannel);
						}
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

	public void startConnectThread() {
		Thread thread = new Thread(() -> {
			while (true) {
				try {
					refreshConnectedHostAddress();
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
		thread.setName("Connect Thread");
		thread.setDaemon(true);
		thread.start();

	}

	private void refreshConnectedHostAddress() {
		for (String hostAddress : IpAddressUtils.getActiveHostAddressesInLAN()) {
			CommunicationChannel communicationChannel = connectedHostAddress.get(hostAddress);
			if (communicationChannel == null) {
				connectToHostAddress(hostAddress);
			}
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

	public CommunicationChannel connectToHostAddress(String hostAddress) {
		CommunicationChannel communicationChannel = connectedHostAddress.get(hostAddress);
		try {
			if (communicationChannel == null) {
				Socket socket = new Socket(hostAddress, PORT);
				communicationChannel = new CommunicationChannel(socket.getInputStream(), socket.getOutputStream());
				connectedHostAddress.put(hostAddress, communicationChannel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return communicationChannel;
	}

	@Override
	public String getHostAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public boolean sendMessage(Message message) {
		try {
			String recipientHostAddress = message.getRecipientHostAddress();
			CommunicationChannel communicationChannel = connectToHostAddress(recipientHostAddress);
			if (communicationChannel != null) {
				communicationChannel.writeData(message.toString());
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
