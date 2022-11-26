package com.chatter.dto;

import java.time.LocalDateTime;

public class MessageDto {

	public LocalDateTime createdTime;

	public String content;

	public String recipientUsername;

	public String senderUsername;

	@Override
	public String toString() {
		return "MessageDto [createdTime=" + createdTime + ", content=" + content + ", recipientUsername="
				+ recipientUsername + ", senderUsername=" + senderUsername + "]";
	}

}
