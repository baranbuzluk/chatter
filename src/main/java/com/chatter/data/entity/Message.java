package com.chatter.data.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

	@Column(name = "recipient_host_address", nullable = false)
	private String recipientHostAddress;

	@Column(name = "sender_host_address", nullable = false)
	private String senderHostAdress;

	Message() {
		createdAt = LocalDateTime.now();
	}

	public Message(String content, String recipientHostAddress, String senderHostAdress) {
		this();
		this.content = content;
		this.recipientHostAddress = recipientHostAddress;
		this.senderHostAdress = senderHostAdress;
	}

	public String getRecipientHostAddress() {
		return recipientHostAddress;
	}

	public Integer getId() {
		return id;
	}

	@JsonIgnore
	public String getSenderHostAdress() {
		if (senderHostAdress != null) {
			return senderHostAdress;
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

	@Override
	public String toString() {
		return String.format("[%s] %s : %s : %s", getCreatedAt(), recipientHostAddress, getContent(), senderHostAdress);
	}

}
