package com.chatter.core.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Message implements Serializable {

	private static final long serialVersionUID = 3147221083926479873L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column(updatable = false)
	private String username;

	@Column(updatable = false)
	private String content;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name = "account_id",nullable = false)
	private Account account;
	
	public Message() {
		createdAt = LocalDateTime.now();
	}

	public Message(String username, String content) {
		setUsername(username);
		setContent(content);
	}

	public Integer getId() {
		return id;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return String.format("[%s] %s : %s", getCreatedAt(), getUsername(), getContent());
	}
	
	
	
}
