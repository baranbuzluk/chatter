package com.chatter.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class PostServiceImpl implements PostService {

	private static final int PORT = 9999;

	private static final int UDP_PORT = 9998;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private List<PostListener> postListeners = new ArrayList<>();

	public PostServiceImpl() {
		startServerSocket();
		startUdpSocket();
	}

	@Override
	public void addPostListener(PostListener postListener) {
		if (!postListeners.contains(postListener)) {
			postListeners.add(postListener);
		}
	}

	private void startServerSocket() {

		Thread thread = new Thread(() -> {
			try (ServerSocket serverSocket = new ServerSocket(PORT)) {
				logger.info("Server was started!");
				while (true) {
					try {
						logger.info("Server is waiting a client.");
						Socket accept = serverSocket.accept();
						acceptSocket(accept);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				String message = "Server could not be established!";
				logger.error(message, e);
				JOptionPane.showMessageDialog(new JFrame(), message, "ERROR OCCURRED", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	private void startUdpSocket() {

		Thread thread = new Thread(() -> {
			try (DatagramSocket serverSocket = new DatagramSocket(UDP_PORT)) {
				byte[] databyte = new byte[512];
				DatagramPacket data = new DatagramPacket(databyte, databyte.length);
				logger.info("Udp server was started");
				while (true) {
					logger.info("Upd server is waiting a stream");
					serverSocket.receive(data);
					acceptUdpStream(data);
				}
			} catch (Exception e) {
				String message = "Server could not be established!";
				logger.error(message, e);
				JOptionPane.showMessageDialog(new JFrame(), message, "ERROR OCCURRED", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		});

		thread.setDaemon(true);
		thread.start();

	}

	private void acceptUdpStream(DatagramPacket data) {
		logger.info("{0} stream was accepted", data.getAddress().getHostAddress());

		try {
			if (data != null) {
				ByteArrayInputStream stream = new ByteArrayInputStream(data.getData());
				new Thread(() -> {
					for (PostListener listener : postListeners) {
						listener.receivedStream(stream);
					}
				}).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void acceptSocket(Socket accept) {
		logger.info(MessageFormat.format("{0} was accepted.", accept.getInetAddress().getHostAddress()));
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(accept.getInputStream());
			Post post = (Post) objectInputStream.readObject();
			objectInputStream.close();
			accept.close();

			new Thread(() -> {
				for (PostListener listener : postListeners) {
					listener.receivedPost(post);
				}
			}).start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean sendPost(Post post, String dstHostAddress) {
		try (Socket socket = new Socket(dstHostAddress, PORT)) {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(post);
			objectOutputStream.close();
			return true;
		} catch (IOException e) {
		}
		return false;
	}

	@Override
	public boolean sendStream(byte[] data, String dstHostAddress) {
		try (DatagramSocket socket = new DatagramSocket(UDP_PORT, InetAddress.getByName(dstHostAddress))) {
			DatagramPacket packet = new DatagramPacket(data, data.length);
			socket.send(packet);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}