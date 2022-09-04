package com.chatter.core.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.util.DigestUtils;

@Entity(name = "account")
public class Account {

	@Id
	@GeneratedValue
	private Integer id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;
	
	@OneToMany(mappedBy = "account")
	private List<Message> messages;

	public Account() {
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

	public void setUsername(String username) {
		this.username = Objects.requireNonNull(username);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = convertMD5(Objects.requireNonNull(password));
	}

	private String convertMD5(String text) {
		if (text == null) {
			return null;
		}
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

	public Collection<Message> getMessages() {
		return Collections.unmodifiableList(this.messages);
	}

	
	public void addMessage(Message message) {
		if(messages == null ) {
			messages = new ArrayList<Message>();
		}
		if(message != null) {
			message.setAccount(this);
			this.messages.add(message);						
		}
	}
	
	public boolean removeMessage(Message message) {
		boolean result = false;
		if( messages != null) {
			result = this.messages.remove(message);
			return result;
		}
		return result;
	}

}
