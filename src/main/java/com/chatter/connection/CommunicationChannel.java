package com.chatter.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class CommunicationChannel {

	private final InputStream inputStream;

	private final OutputStream outputStream;

	private final List<Byte> buffer = new ArrayList<>();

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
		byte[] processedData = processIODataToWrite(data);
		writeData(processedData);
	}

	private static byte[] processIODataToWrite(String data) {
		byte[] bytesOfData = data.getBytes();
		byte[] newArray = new byte[bytesOfData.length + 2];
		newArray[0] = ASCIIControlChar.SOT;
		newArray[newArray.length - 1] = ASCIIControlChar.ETX;
		System.arraycopy(bytesOfData, 0, newArray, 1, bytesOfData.length);
		return newArray;
	}

	public byte[] getRawData() throws IOException {
		byte[] rawData = new byte[0];
		int available = inputStream.available();
		if (available > 0) {
			rawData = new byte[available];
			for (int i = 0; i < available; i++) {
				rawData[i] = (byte) inputStream.read();
			}
		}
		return rawData;
	}

	public synchronized List<String> getProcessedData() throws IOException {
		List<String> messages = new ArrayList<>();
		int available = inputStream.available();
		for (int i = 0; i < available; i++) {
			byte data = (byte) inputStream.read();
			if (data == ASCIIControlChar.SOT) {
				buffer.clear();
			} else if (data == ASCIIControlChar.ETX) {
				messages.add(getData(buffer));
			} else if (data == ASCIIControlChar.EOT) {
				outputStream.close();
				inputStream.close();
				buffer.clear();
				break;
			} else {
				buffer.add(data);
			}
		}
		return messages;
	}

	private String getData(List<Byte> srcBytes) {
		Object[] srcBytesArray = srcBytes.toArray();
		byte[] array = new byte[srcBytesArray.length];
		for (int i = 0; i < srcBytesArray.length; i++) {
			array[i] = (byte) srcBytesArray[i];
		}
		return new String(array);
	}

	public void registerListener(CommunicationChannelListener listener) {
		Executors.newScheduledThreadPool(1, r -> {
			Thread thread = new Thread(r);
			thread.setDaemon(true);
			return thread;
		}).scheduleAtFixedRate(() -> {
			try {
				if (inputStream.available() > 0) {
					List<String> processedData = getProcessedData();
					for (String message : processedData) {
						listener.messageReceived(message);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}, 0, 500, TimeUnit.MILLISECONDS);
	}

}
