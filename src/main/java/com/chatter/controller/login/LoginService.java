package com.chatter.controller.login;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.core.ChatterService;
import com.chatter.core.ControllerNotInitializedException;
import com.chatter.entity.Account;
import com.chatter.listener.EventInfo;
import com.chatter.listener.EventManager;
import com.chatter.main.MainViewService;
import com.chatter.repository.AccountRepository;

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
		try {
			this.controller = new LoginController(this);
			eventManager.registerListener(controller);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ControllerNotInitializedException();
		}
	}

	public Account getByUsername(String username) {
		return accountRepository.getByUsername(Objects.requireNonNull(username));
	}

	public void sendEvent(EventInfo event) {
		eventManager.sendEvent(Objects.requireNonNull(event));
	}

	public void showInMainWindow(Pane rootPane) {
		mainWindowService.show(Objects.requireNonNull(rootPane, "Pane can not be null!"));
	}
}
