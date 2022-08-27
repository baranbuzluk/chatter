package com.chatter.core.util;

import java.util.Objects;

import com.chatter.core.entity.Message;

public final class MessageUtil {

	private static final String SPLITTER = "X:X";

	private MessageUtil() {
	}

	public static String parseMessage(Message fromMessage) {
		Message fromMsg = Objects.requireNonNull(fromMessage, "message must not be null!");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(fromMsg.getUsername());
		stringBuilder.append(SPLITTER);
		stringBuilder.append(fromMsg.getContent());
		return stringBuilder.toString();
	}

	public static Message convertToMessage(String fromString) {
		String fromStr = Objects.requireNonNull(fromString, "message must not be null!");
		String[] split = fromStr.split(SPLITTER);
		if (split.length != 2) {
			return null;
		}
		String username = split[0];
		String content = split[1];
		Message toMsg = new Message();
		toMsg.setUsername(username);
		toMsg.setContent(content);
		return toMsg;
	}

}
