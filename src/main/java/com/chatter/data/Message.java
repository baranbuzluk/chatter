package com.chatter.data;

import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message implements Serializable {

	private static final long serialVersionUID = -1931015252176087946L;

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	private LocalDateTime createdTime;

	private String content;

	private byte[] imageContent;

	private String senderHostAddress;

	private String recipientHostAddress;

	public String getSenderHostAddress() {
		return senderHostAddress;
	}

	public void setSenderHostAddress(String senderHostAddress) {
		this.senderHostAddress = senderHostAddress;
	}

	public String getRecipientHostAddress() {
		return recipientHostAddress;
	}

	public void setRecipientHostAddress(String recipientHostAddress) {
		this.recipientHostAddress = recipientHostAddress;
	}

	@Override
	public String toString() {
		return MessageFormat.format("[{0}] : {1}", getFormattedCreatedTime(), content);
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public String getFormattedCreatedTime() {
		String dateTime = "";
		if (createdTime != null) {
			dateTime = createdTime.format(DATE_TIME_FORMATTER);
		}
		return dateTime;

	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte[] getImageContent() {
		return imageContent;
	}

	public void setImageContent(byte[] imageContent) {
		this.imageContent = imageContent;
	}

}
