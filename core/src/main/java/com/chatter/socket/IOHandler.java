package com.chatter.socket;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public abstract class IOHandler {

	private volatile boolean runLoop;

	private PrintWriter printWriter;

	private BufferedReader bufferedReader;

	protected abstract void receivedMessage(String message);

	public final void sendMessage(String message) {
		Objects.requireNonNull(message, "message must not be null!");
		if (printWriter != null) {
			printWriter.println(message);
		}
	}

	protected final void setOutputStream(OutputStream outputStream) {
		printWriter = new PrintWriter(outputStream, true, StandardCharsets.UTF_8);
	}

	protected final void setInputStream(InputStream inputStream) {
		if (runLoop) {
			return;
		}
		runLoop = true;
		bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
		Thread thread = new Thread(new RunLoop(), "Input Worker");
		thread.setDaemon(true);
		thread.start();
	}

	private class RunLoop implements Runnable {
		@Override
		public void run() {
			try {
				String readLine;
				while (runLoop) {
					if ((readLine = bufferedReader.readLine()) != null) {
						receivedMessage(readLine);
					}
				}
				bufferedReader.close();
			} catch (Exception e) {
				System.err.println("Input Worker is terminating... Caused by.:" + e.getLocalizedMessage());
			}
			runLoop = false;
			bufferedReader = null;
			receivedMessage("STOP");
		}
	}
}
