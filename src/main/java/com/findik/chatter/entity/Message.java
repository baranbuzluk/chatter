package com.findik.chatter.entity;

import javax.persistence.Entity;

@Entity
public class Message extends BaseEntity {

	private static final long serialVersionUID = 2971978872664554603L;

	private String username;

	private String content;

	public Message() {
	}

	public Message(String username, String content) {
		this.username = username;
		this.content = content;
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

	@Override
	public String toString() {
		return String.format("[%s] %s : %s", getCreatedAtStr(), getUsername(), getContent());
	}

}
