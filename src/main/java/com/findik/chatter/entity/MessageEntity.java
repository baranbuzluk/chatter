package com.findik.chatter.entity;

import java.math.BigInteger;

public class MessageEntity {

	private BigInteger messageID;
	private String username;
	private String content;

	public MessageEntity(BigInteger messageID, String username, String content) {
		this.messageID = messageID;
		this.username = username;
		this.content = content;
	}

	public BigInteger getMessageID() {
		return messageID;
	}

	public void setMessageID(BigInteger messageID) {
		this.messageID = messageID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
