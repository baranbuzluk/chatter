package com.chatter.dto;

import java.time.LocalDateTime;

import com.chatter.post.Post;

public class MessageDto implements Post {

	private static final long serialVersionUID = -3055662042621540264L;

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
