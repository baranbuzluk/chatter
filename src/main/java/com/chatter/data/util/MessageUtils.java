package com.chatter.data.util;

import java.io.File;
import java.text.MessageFormat;

import com.chatter.data.entity.Message;

public final class MessageUtils {

	private MessageUtils() {
	}

	public static void write(Message srcData, FileOutputType outputType) {

		try {
			File dstFile = getMessageDirectory();
			if (srcData != null) {
				String fileName = MessageFormat.format("Message-{0}{1}", System.nanoTime(),
						outputType.getFileExtension());
				File messageFile = new File(dstFile, fileName);
				outputType.getObjectMapper().writerFor(Message.class).writeValue(messageFile, srcData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static File getMessageDirectory() {
		String userHomePath = System.getProperty("user.home");
		File dstFile = new File(userHomePath, "CHATTER_MSG");
		if (!dstFile.exists()) {
			dstFile.mkdir();
		}
		return dstFile;
	}

}
