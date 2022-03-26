package com.findik.chatter.window.login.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.abstracts.window.IWindow;
import com.findik.chatter.entity.Account;
import com.findik.chatter.main.api.IMainWindowService;
import com.findik.chatter.repository.IAccountRepository;
import com.findik.chatter.window.client.api.IChatterClientWindow;
import com.findik.chatter.window.login.view.LoginController;

import javafx.scene.layout.StackPane;

@Component
public class LoginWindow implements IWindow {

	@Autowired
	private IAccountRepository accountRepository;

	@Autowired
	private IMainWindowService mainWindowService;

	@Autowired
	private IChatterClientWindow chatterClientWindow;

	private LoginController controller;

	@PostConstruct
	private void postConstruct() {
		controller = new LoginController();
		mainWindowService.setInnerPane(controller.getPane());
		controller.setLoginButtonOnClickedEvent(account -> executeLoginOperations(account));
	}

	private void executeLoginOperations(Account account) {
		String username = account.getUsername();
		Account accountFromDb = accountRepository.getByUsername(username);
		if (account.equals(accountFromDb)) {
			StackPane chatterClientWindowPane = chatterClientWindow.getPane();
			mainWindowService.setInnerPane(chatterClientWindowPane);
		}
	}

	@Override
	public StackPane getPane() {
		return controller.getPane();
	}
}
