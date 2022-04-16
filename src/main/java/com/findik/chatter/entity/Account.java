package com.findik.chatter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

	public Account() {
	}

	public Account(String username, String password) {
		this();
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
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = convertMD5(password);
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
		if (!(obj instanceof Account)) {
			return false;
		} else if (obj == this) {
			return true;
		}
		Account o = (Account) obj;
		return this.username.equals(o.username) && this.password.equals(o.password);
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (this.username != null) {
			result = 31 * result + this.username.hashCode();
		}
		if (this.password != null) {
			result = 31 * result + this.password.hashCode();
		}
		return result;

	}

}
