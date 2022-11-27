package com.chatter.service.impl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Entity(name = "account")
class Account implements Serializable {

	private static final long serialVersionUID = 5451791209544938318L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(name = "ip_address")
	private String ipAddress;

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
		this.password = password;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}

@Repository
interface AccountRepository extends JpaRepository<Account, Integer> {

	boolean existsByUsername(String username);

	Account findByUsernameAndPassword(String username, String password);

	Account findByUsername(String username);

}
