package com.chatter.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.entity.Account;
import com.chatter.repository.AccountRepository;

@Component
public class InitializeApplication {

	@Autowired
	private AccountRepository accountRepository;

	@PostConstruct
	void postConstruct() {
		initAccount();
	}

	private void initAccount() {
		String username = "username";
		if (accountRepository.getByUsername(username) == null) {
			String password = "password";
			Account defaultAccount = new Account(username, password);
			accountRepository.save(defaultAccount);
		}

	}

}
