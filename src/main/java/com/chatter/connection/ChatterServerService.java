package com.chatter.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ChatterServerService {

	private static Logger LOG = LoggerFactory.getLogger(ChatterServerService.class);

	private static final int PORT = 9999;

	private volatile boolean isOpened;

	private Thread thread;

	private final Map<CommunicationChannel, Socket> connectedSockets = new HashMap<>();

	public synchronized void openServer() {
		if (isOpened) {
			return;
		}

		isOpened = true;
		thread = new Thread(() -> {

			try (ServerSocket serverSocket = new ServerSocket(PORT)) {
				LOG.info("Server opened.");
				Socket accept;
				while (isOpened && ((accept = serverSocket.accept()) != null)) {
					OutputStream outputStream = accept.getOutputStream();
					InputStream inputStream = accept.getInputStream();
					CommunicationChannel ioHandler = new CommunicationChannel(inputStream, outputStream);
					connectedSockets.put(ioHandler, accept);
				}
			} catch (Exception e) {
				LOG.info("Error occurred!", e);
			}

		});

		thread.start();

		startClientWorker();

	}

	public synchronized void closeServer() {
		if (!isOpened) {
			return;
		}

		isOpened = false;
		while (thread.isAlive()) {
			// wait
		}

		thread = null;
		connectedSockets.clear();
		LOG.info("Server closed.");

	}

	private void startClientWorker() {

		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
			Map<CommunicationChannel, byte[]> rawDatasToDispatch = new HashMap<>();

			Set<CommunicationChannel> chatterIOHandlers = connectedSockets.keySet();
			for (CommunicationChannel handler : chatterIOHandlers) {
				try {
					byte[] rawData = handler.getRawData();
					System.out.println(new String(rawData));
					if (rawData.length > 0) {
						rawDatasToDispatch.put(handler, rawData);
					}
				} catch (Exception e) {
					e.printStackTrace();
					try {
						Socket remove = connectedSockets.remove(handler);
						remove.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

			dispatch(rawDatasToDispatch);

		}, 0, 200, TimeUnit.MILLISECONDS);

	}

	private void dispatch(Map<CommunicationChannel, byte[]> rawDataToDispatch) {

		for (Map.Entry<CommunicationChannel, byte[]> entry : rawDataToDispatch.entrySet()) {
			CommunicationChannel key = entry.getKey();
			byte[] val = entry.getValue();

			for (CommunicationChannel chatterIOHandler : connectedSockets.keySet()) {
				if (key != chatterIOHandler) {
					try {
						chatterIOHandler.writeData(val);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
