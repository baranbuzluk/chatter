package com.chatter.service.impl;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Entity(name = "message")
class Message implements Serializable {

	private static final long serialVersionUID = 3147221083926479873L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column(updatable = false)
	private String content;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@JoinColumn(name = "recipient_account_id", referencedColumnName = "id")
	@ManyToOne
	private Account recipientAccount;

	@JoinColumn(name = "sender_account_id", referencedColumnName = "id")
	@ManyToOne
	private Account senderAccount;

	public Message() {
		createdAt = LocalDateTime.now();
	}

	public Integer getId() {
		return id;
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

	public Account getRecipientAccount() {
		return recipientAccount;
	}

	public void setRecipientAccount(Account recipientAccount) {
		this.recipientAccount = recipientAccount;
	}

	public Account getSenderAccount() {
		return senderAccount;
	}

	public void setSenderAccount(Account senderAccount) {
		this.senderAccount = senderAccount;
	}

}

@Repository
interface MessageRepository extends JpaRepository<Message, Integer> {

	List<Message> findAllByOrderByCreatedAtAsc();

}
