package com.chatter.data.util;

import java.io.File;
import java.text.MessageFormat;

import com.chatter.data.entity.Message;

public final class MessageUtils {

	private MessageUtils() {
	}

	public static void write(Message srcData, File dstFile, FileOutputType outputType) {
		try {
			boolean checkFile = dstFile != null && dstFile.exists() && dstFile.isDirectory();
			if (srcData != null && checkFile) {
				String fileName = MessageFormat.format("Message-{0}{1}", System.nanoTime(),
						outputType.getFileExtension());

				File messageFile = new File(dstFile, fileName);
				outputType.getObjectMapper().writerFor(Message.class).writeValue(messageFile, srcData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
