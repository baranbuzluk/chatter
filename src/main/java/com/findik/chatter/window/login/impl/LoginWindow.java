package com.findik.chatter.window.login.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.abstracts.IMainWindowService;
import com.findik.chatter.abstracts.IWindow;
import com.findik.chatter.entity.Account;
import com.findik.chatter.listener.IStartedApplicationEventListener;
import com.findik.chatter.repository.IAccountRepository;
import com.findik.chatter.window.client.api.IChatterClientWindow;
import com.findik.chatter.window.login.view.LoginController;

import javafx.scene.layout.StackPane;

@Component
public class LoginWindow implements IWindow, IStartedApplicationEventListener {

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
		controller.setLoginButtonOnClickedEvent(this::executeLoginOperations);
	}

	private void executeLoginOperations(Account account) {
		String username = account.getUsername();
		Account accountFromDb = accountRepository.getByUsername(username);
		if (account.equals(accountFromDb)) {
			StackPane chatterClientWindowPane = chatterClientWindow.getPane();
			mainWindowService.show(chatterClientWindowPane);
		}
	}

	@Override
	public StackPane getPane() {
		return controller.getPane();
	}

	@Override
	public void startedApplicationEvent() {
		StackPane pane = controller.getPane();
		mainWindowService.show(pane);
	}
}
