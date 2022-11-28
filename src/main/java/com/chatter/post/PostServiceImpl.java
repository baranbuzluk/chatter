package com.chatter.post;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.MessageFormat;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class PostServiceImpl implements PostService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final int PORT = 9998;

	private PostListener messageListener;

	public PostServiceImpl() {
		startServerSocket();
	}

	@Override
	public void setPostListener(PostListener messageListener) {
		this.messageListener = messageListener;
	}

	private void startServerSocket() {

		logger.info("Server was started!");
		Thread thread = new Thread(() -> {
			try (ServerSocket serverSocket = new ServerSocket(PORT);) {
				while (true) {
					try {
						logger.info("It is being waited for client");
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
			if (messageListener != null) {
				messageListener.receivedPost(post);
			}
			accept.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean sendPost(Post post, String hostAddress) {
		try (Socket socket = new Socket(hostAddress, PORT)) {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(post);
			objectOutputStream.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
