package com.findik.chatter.window.login.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.abstracts.AbstractGuiWindow;
import com.findik.chatter.abstracts.IMainWindowService;
import com.findik.chatter.entity.Account;
import com.findik.chatter.enums.ChatterEvent;
import com.findik.chatter.enums.ChatterEventProperties;
import com.findik.chatter.listener.api.EventListener;
import com.findik.chatter.listener.api.EventManager;
import com.findik.chatter.listener.impl.EventInfo;
import com.findik.chatter.repository.IAccountRepository;
import com.findik.chatter.window.login.view.LoginController;

import javafx.application.Platform;

@Component
public class LoginWindow extends AbstractGuiWindow<LoginController> implements EventListener {

	@Autowired
	private IAccountRepository accountRepository;

	@Autowired
	private IMainWindowService mainWindowService;

	@Autowired
	private EventManager eventManager;

	@Override
	protected void afterControllerLoaded() {
		controller.setLoginButtonOnClickedEvent(this::executeLoginOperations);
	}

	private void executeLoginOperations(Account account) {
		String username = account.getUsername();
		Account accountFromDb = accountRepository.getByUsername(username);
		if (account.equals(accountFromDb)) {
			EventInfo event = new EventInfo(ChatterEvent.LOGGED_IN_ACCOUNT);
			event.put(ChatterEventProperties.ACCOUNT, accountFromDb);
			eventManager.sendEvent(event);
		}
	}

	@Override
	protected LoginController createController() {
		return new LoginController();
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ChatterEvent.STARTED_APPLICATION) {
			Platform.runLater(() -> mainWindowService.show(getRootPane()));
		}
		eventManager.stopNotifyingListeners();
	}
}
