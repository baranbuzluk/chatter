package com.chatter.client.controller.register;

import java.util.Objects;

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
public class RegisterService implements ChatterService {
	private AccountRepository accountRepository;

	private MainViewService mainWindowService;

	private EventManager eventManager;

	private RegisterController controller;

	@Autowired
	public RegisterService(AccountRepository accountRepository, MainViewService mainWindowService,
			EventManager eventManager) {
		this.accountRepository = accountRepository;
		this.mainWindowService = mainWindowService;
		this.eventManager = eventManager;
		this.controller = new RegisterController(this);
		eventManager.registerListener(controller);
	}

	public void showInMainWindow(Pane rootPane) {
		mainWindowService.show(Objects.requireNonNull(rootPane, "Pane can not be null!"));
	}

	public void sendEvent(EventInfo event) {
		eventManager.sendEvent(Objects.requireNonNull(event));
	}

	public Account getByUsername(String username) {
		return accountRepository.getByUsername(Objects.requireNonNull(username));
	}
}
