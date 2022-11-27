package com.chatter.service.impl;

import java.net.InetAddress;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import com.chatter.dto.AccountDto;
import com.chatter.event.ChatterEvent;
import com.chatter.event.EventInfo;
import com.chatter.event.EventService;
import com.chatter.event.Variable;
import com.chatter.service.LoginService;
import com.chatter.service.RegistrationService;

@Component
class AccountOperationServiceImpl implements RegistrationService, LoginService {

	private AccountRepository accountRepository;

	private EventService eventService;

	@Autowired
	public AccountOperationServiceImpl(AccountRepository accountRepository, EventService eventService) {
		this.accountRepository = accountRepository;
		this.eventService = eventService;
	}

	@Override
	public boolean existsUsername(String username) {
		return accountRepository.existsByUsername(username);
	}

	@Override
	public boolean registerAccount(AccountDto accountDto) {
		Objects.requireNonNull(accountDto, "accountDto must not be null!");
		Objects.requireNonNull(accountDto.password, "accountDto.password must not be null!");
		Objects.requireNonNull(accountDto.username, "accountDto.username must not be null!");

		if (existsUsername(accountDto.username)) {
			return false;
		}

		Account account = new Account();
		account.setUsername(accountDto.username);

		String hashedPassword = convertMD5(accountDto.password);
		account.setPassword(hashedPassword);
		accountRepository.saveAndFlush(account);

		return true;

	}

	@Override
	public boolean login(String username, String password) {
		String hashedPassword = convertMD5(password);
		Account account = accountRepository.findByUsernameAndPassword(username, hashedPassword);
		boolean login = account != null;
		if (login) {
			try {
				String hostAddress = InetAddress.getLocalHost().getHostAddress();
				account.setIpAddress(hostAddress);
				accountRepository.saveAndFlush(account);
			} catch (Exception e) {
				e.printStackTrace();
			}

			EventInfo event = new EventInfo(ChatterEvent.LOGGED_IN_ACCOUNT);
			event.putVariable(Variable.USERNAME, username);
			eventService.sendEvent(event);
		}
		return login;
	}

	private String convertMD5(String text) {
		byte[] bytesOfText = text.getBytes();
		return DigestUtils.md5DigestAsHex(bytesOfText);
	}

}
