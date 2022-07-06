package com.chatter.client.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.client.main.MainViewService;
import com.chatter.core.ChatterService;
import com.chatter.data.entity.Account;
import com.chatter.data.repository.AccountRepository;
import com.chatter.listener.api.EventInfo;
import com.chatter.listener.api.EventManager;

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

	public boolean checkAccount(Account account) {
		if (account == null) {
			return false;
		}
		String username = account.getUsername();
		Account fromDB = accountRepository.getByUsername(username);
		return account.equals(fromDB);

	}
}
