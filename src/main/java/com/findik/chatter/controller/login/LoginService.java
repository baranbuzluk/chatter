package com.findik.chatter.controller.login;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.core.ChatterService;
import com.findik.chatter.entity.Account;
import com.findik.chatter.enums.ChatterEvent;
import com.findik.chatter.listener.EventInfo;
import com.findik.chatter.listener.EventListener;
import com.findik.chatter.listener.EventManager;
import com.findik.chatter.main.MainViewService;
import com.findik.chatter.repository.AccountRepository;

import javafx.application.Platform;

@Component
public class LoginService implements ChatterService, EventListener {

	private AccountRepository accountRepository;

	@Autowired
	private MainViewService mainWindowService;

	@Autowired
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
		} catch (IOException e) {
			this.controller = null;
			e.printStackTrace();
		}
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ChatterEvent.STARTED_APPLICATION) {
			Platform.runLater(() -> mainWindowService.show(controller.getPane()));
		}
	}

	public Account getByUsername(String username) {
		return accountRepository.getByUsername(Objects.requireNonNull(username));
	}

	public void sendEvent(EventInfo event) {
		eventManager.sendEvent(Objects.requireNonNull(event));
	}
}
