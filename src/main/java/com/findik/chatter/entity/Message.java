package com.findik.chatter.entity;

import java.math.BigInteger;

public class Message {

	private BigInteger id;
	private String username;
	private String content;

	public Message() {

	}

	public Message(String username, String content) {
		this.username = username;
		this.content = content;

	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
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
