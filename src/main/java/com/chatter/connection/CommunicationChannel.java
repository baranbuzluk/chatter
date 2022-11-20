package com.chatter.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Arrays;

import com.chatter.connection.processor.DataProcessor;

public final class CommunicationChannel {

	private final InputStream inputStream;

	private final OutputStream outputStream;

	public CommunicationChannel(InputStream inputStream, OutputStream outputStream) {
		this.inputStream = inputStream;
		this.outputStream = outputStream;
	}

	public synchronized void writeData(byte data) throws IOException {
		outputStream.write(data);
		outputStream.flush();
	}

	public void writeData(byte[] data) throws IOException {
		for (int i = 0; i < data.length; i++) {
			writeData(data[i]);
		}
	}

	public void writeData(String data) throws IOException {
		byte[] processedData = DataProcessor.processSendingData(data.getBytes());
		writeData(processedData);
	}

	public synchronized List<String> getProcessedData() throws IOException {
		List<String> messages = new ArrayList<>();
		int available = inputStream.available();
		byte endOfTransmission = DataProcessor.getEndOfTransmission();
		byte[] buffer = new byte[4000];
		int bufferPosition = 0;
		for (int i = 0; i < available; i++) {
			byte read = (byte) inputStream.read();
			if (read == endOfTransmission) {
				byte[] data = Arrays.copyOfRange(buffer, 0, bufferPosition);
				byte[] processReceivingData = DataProcessor.processReceivingData(data);
				messages.add(new String(processReceivingData));
				bufferPosition = 0;
			} else {
				buffer[bufferPosition++] = read;
			}
		}
		return messages;
	}

	public List<String> getMessage() {
		try {
			return getProcessedData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

}
