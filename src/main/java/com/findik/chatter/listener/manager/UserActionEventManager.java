package com.findik.chatter.listener.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.abstracts.AbstractEventManager;
import com.findik.chatter.entity.Account;
import com.findik.chatter.listener.api.IUserLoggedInEventListener;

@Component
public class UserActionEventManager extends AbstractEventManager {

	@Autowired
	List<IUserLoggedInEventListener> userLoggedInEventListeners;

	public void notiftUserLoggedInEventListeners(Account account) {
		notifyListeners(IUserLoggedInEventListener.class, userLoggedInEventListeners, account);
	}
}
