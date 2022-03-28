package com.findik.chatter.window.login.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.abstracts.AbstractGuiWindow;
import com.findik.chatter.abstracts.IMainWindowService;
import com.findik.chatter.entity.Account;
import com.findik.chatter.listener.api.IApplicationStartedEventListener;
import com.findik.chatter.listener.manager.UserActionEventManager;
import com.findik.chatter.repository.IAccountRepository;
import com.findik.chatter.window.login.view.LoginController;

import javafx.scene.layout.StackPane;

@Component
public class LoginWindow extends AbstractGuiWindow<LoginController> implements IApplicationStartedEventListener {

	@Autowired
	private IAccountRepository accountRepository;

	@Autowired
	private IMainWindowService mainWindowService;

	@Autowired
	private UserActionEventManager userActionEventManager;

	@Override
	protected void afterControllerLoaded() {
		controller.setLoginButtonOnClickedEvent(this::executeLoginOperations);
	}

	private void executeLoginOperations(Account account) {
		String username = account.getUsername();
		Account accountFromDb = accountRepository.getByUsername(username);
		if (account.equals(accountFromDb)) {
			userActionEventManager.notiftUserLoggedInEventListeners(account);
		}
	}

	@Override
	public void updateApplicationStartedEvent() {
		StackPane pane = getRootPane();
		mainWindowService.show(pane);
	}

	@Override
	protected LoginController createController() {
		return new LoginController();
	}
}
