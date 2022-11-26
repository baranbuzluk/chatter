package com.chatter.communication.processor;

import java.util.Arrays;

public final class DataProcessor {

	private DataProcessor() {
	}

	public static final byte[] processSendingData(byte[] sendingData) {
		byte[] processedData = new byte[sendingData.length + 2];
		processedData[0] = ASCIIControlChar.SOT;
		processedData[processedData.length - 1] = ASCIIControlChar.ETX;
		System.arraycopy(sendingData, 0, processedData, 1, sendingData.length);
		return processedData;
	}

	public static final byte[] processReceivingData(byte[] receivingData) {
		int indexStartOfText = -1;
		int indexEndOfText = -1;

		for (int i = 0; i < receivingData.length; i++) {
			byte data = receivingData[i];
			if (data == ASCIIControlChar.SOT) {
				indexStartOfText = i;
			} else if (data == ASCIIControlChar.ETX) {
				indexEndOfText = i;
				break;
			}
		}

		return Arrays.copyOfRange(receivingData, indexStartOfText + 1, indexEndOfText);
	}

	public static final byte getEndOfTransmission() {
		return ASCIIControlChar.EOT;
	}
}
