package com.findik.chatter.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAliasType;

@Entity
@XStreamAliasType(value = "Message")
public class Message implements Serializable {

	private static final long serialVersionUID = 3147221083926479873L;

	@Id
	@GeneratedValue
	@XStreamAlias(value = "Id")
	private Integer id;

	@Column(updatable = false)
	@XStreamAlias(value = "Username")
	private String username;

	@Column(updatable = false)
	@XStreamAlias(value = "Content")
	private String content;

	@Column(name = "created_at", nullable = false, updatable = false)
	@XStreamAlias(value = "Created")
	private LocalDateTime createdAt;

	public Message() {
		createdAt = LocalDateTime.now();
	}

	public Message(String username, String content) {
		this();
		this.username = username;
		this.content = content;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return String.format("[%s] %s : %s", getCreatedAt(), getUsername(), getContent());
	}

}
