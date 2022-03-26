package com.findik.chatter.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.entity.Account;
import com.findik.chatter.repository.IAccountRepository;

@Component
public class InitializeApplication {

	@Autowired
	private IAccountRepository accountRepository;

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
