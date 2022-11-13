package com.chatter.data.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.util.DigestUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "account")
public class Account implements Serializable {

	private static final long serialVersionUID = 5451791209544938318L;

	@Id
	@GeneratedValue
	@JsonIgnore
	private Integer id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	@JsonIgnore
	private String password;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Message> messages;

	/**
	 * Default constructor for hibernate
	 */
	Account() {
		this("", "");
	}

	public Account(String username, String password) {
		setUsername(username);
		setPassword(password);
	}

	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	private void setUsername(String username) {
		this.username = Objects.requireNonNull(username);
	}

	public String getPassword() {
		return password;
	}

	private void setPassword(String password) {
		this.password = convertMD5(Objects.requireNonNull(password));
	}

	private String convertMD5(String text) {
		byte[] bytesOfText = text.getBytes();
		return DigestUtils.md5DigestAsHex(bytesOfText);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Account o)) {
			return false;
		} else if (obj == this) {
			return true;
		}
		return this.username.equals(o.username) && this.password.equals(o.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, password, id);
	}

	public void addMessage(Message message) {
		if (messages == null) {
			messages = new ArrayList<>();
		}

		if (message != null && !messages.contains(message)) {
			this.messages.add(message);
		}
	}
}
