package com.chatter.client.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.client.enums.ClientEvent;
import com.chatter.client.enums.ClientEventProperties;
import com.chatter.client.main.MainViewService;
import com.chatter.client.session.ChatterSession;
import com.chatter.core.abstracts.ChatterService;
import com.chatter.core.entity.Account;
import com.chatter.core.event.listener.EventInfo;
import com.chatter.core.event.listener.EventManager;
import com.chatter.core.repository.AccountRepository;

import javafx.scene.layout.Pane;

@Component
public class LoginService implements ChatterService {

	private AccountRepository accountRepository;

	private MainViewService mainWindowService;

	private EventManager eventManager;

	private LoginController controller;

	@Autowired
	public LoginService(AccountRepository accountRepository, MainViewService mainWindowService,
			EventManager eventManager) {
		this.accountRepository = accountRepository;
		this.mainWindowService = mainWindowService;
		this.eventManager = eventManager;
		this.controller = new LoginController(this);
		eventManager.registerListener(controller);
	}

	public void sendEvent(EventInfo event) {
		eventManager.sendEvent(event);
	}

	public void showInMainWindow(Pane rootPane) {
		mainWindowService.show(rootPane);
	}

	public boolean login(String username, String password) {
		if (username == null || password == null || !accountRepository.existsByUsername(username)) {
			return false;
		}

		Account accountFromController = new Account(username, password);
		Account accountFromDB = accountRepository.getByUsername(username);
		boolean checkLoginInfo = accountFromDB.equals(accountFromController);

		if (checkLoginInfo) {
			ChatterSession.getInstance().openSession(accountFromDB);
			EventInfo event = new EventInfo(ClientEvent.LOGGED_IN_ACCOUNT);
			event.put(ClientEventProperties.ACCOUNT, accountFromDB);
			sendEvent(event);
		}

		return checkLoginInfo;

	}

}
