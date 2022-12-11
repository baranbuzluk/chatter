package com.chatter.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

	private Logger logger = LoggerFactory.getLogger(getClass());

	private List<PostListener> postListeners = new ArrayList<>();

	public PostServiceImpl() {
		startServerSocket();
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

}