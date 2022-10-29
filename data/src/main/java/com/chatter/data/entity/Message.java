package com.chatter.data.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "message")
public class Message implements Serializable {

	private static final long serialVersionUID = 3147221083926479873L;

	@Id
	@GeneratedValue
	@JsonIgnore
	private Integer id;

	@Column(updatable = false)
	private String content;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	Message() {
		createdAt = LocalDateTime.now();
	}

	public Message(Account account, String content) {
		this();
		setAccount(account);
		setContent(content);
	}

	public Integer getId() {
		return id;
	}

	@JsonIgnore
	public String getUsername() {
		if (account != null) {
			return account.getUsername();
		}
		return "";
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
		this.account = Objects.requireNonNull(account, "account must not be null!");
	}

	@Override
	public String toString() {
		return String.format("[%s] %s : %s", getCreatedAt(), getUsername(), getContent());
	}

}
