package com.chatter.service;

import com.chatter.dto.AccountDto;

public interface RegistrationService {

	boolean existsUsername(String username);

	boolean registerAccount(AccountDto accountDto);
}
