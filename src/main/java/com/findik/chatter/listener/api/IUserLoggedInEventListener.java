package com.findik.chatter.listener.api;

import com.findik.chatter.entity.Account;

public interface IUserLoggedInEventListener {
	void updateUserLoggedInEvent(Account account);
}
