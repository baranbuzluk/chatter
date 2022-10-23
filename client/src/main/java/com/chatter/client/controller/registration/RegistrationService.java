package com.chatter.client.controller.registration;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.client.enums.ClientEvent;
import com.chatter.client.main.MainViewService;
import com.chatter.core.abstracts.ChatterService;
import com.chatter.core.entity.Account;
import com.chatter.core.repository.AccountRepository;
import com.chatter.event.api.EventInfo;
import com.chatter.event.api.EventManager;

import javafx.scene.layout.Pane;

@Component
public class RegistrationService implements ChatterService {

	private AccountRepository accountRepository;

	private EventManager eventManager;

	private MainViewService mainViewService;

	@Autowired
	public RegistrationService(AccountRepository accountRepository, EventManager eventManager,
			MainViewService mainViewService) {
		this.accountRepository = accountRepository;
		this.eventManager = eventManager;
		this.mainViewService = mainViewService;
		RegistrationController controller = new RegistrationController(this);
		eventManager.registerListener(controller);
	}

	public boolean existsByUsername(String username) {
		return accountRepository.existsByUsername(Objects.requireNonNull(username));
	}

	public void showPaneInMainScene(Pane pane) {
		mainViewService.show(pane);
	}

	public void sendEvent(EventInfo eventInfo) {
		eventManager.sendEvent(eventInfo);
	}

	public boolean registerAccount(String username, String password) {
		Account savedAccount = null;
		try {
			Account accountToRegister = new Account(username, password);
			savedAccount = accountRepository.save(accountToRegister);
		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean isRegistered = savedAccount != null;
		if (isRegistered) {
			RegistrationControllerUtils.showSuccessfulSignUpAlertMessage();
			EventInfo event = new EventInfo(ClientEvent.STARTED_APPLICATION);
			sendEvent(event);
		}
		return isRegistered;

	}

}
