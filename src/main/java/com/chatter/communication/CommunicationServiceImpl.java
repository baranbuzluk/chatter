package com.chatter.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.MessageFormat;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.chatter.dto.MessageDto;

@Component
class CommunicationServiceImpl implements CommunicationService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final int PORT = 9998;

	private IOStreamHandler<MessageDto> streamHandler = MessageFileHandler.JSON;

	private ServerSocket serverSocket;

	private MessageListener messageListener;

	public CommunicationServiceImpl() {
		startServerSocket();
	}

	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}

	private void startServerSocket() {
		try {
			serverSocket = new ServerSocket(PORT);
			logger.info("Server was started!");
			Thread thread = new Thread(() -> {
				while (true) {
					try {
						logger.info("It is being waited for client");
						Socket accept = serverSocket.accept();
						acceptSocket(accept);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			thread.setDaemon(true);
			thread.start();

		} catch (IOException e) {
			String message = "Server could not be established!";
			logger.error(message, e);
			JOptionPane.showMessageDialog(new JFrame(), message, "ERROR OCCURRED", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

	}

	private void acceptSocket(Socket accept) {
		logger.info(MessageFormat.format("{0} was accepted.", accept.getInetAddress().getHostAddress()));
		try {
			MessageDto messageDto = streamHandler.read(accept.getInputStream());
			if (messageListener != null) {
				messageListener.receivedMessage(messageDto);
			}
			accept.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean sendMesssage(MessageDto messageDto, String hostAddress) {
		try (Socket socket = new Socket(hostAddress, PORT)) {
			MessageFileHandler.JSON.write(messageDto, socket.getOutputStream());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
