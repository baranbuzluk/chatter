package com.chatter.client.controller.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.client.main.MainViewService;
import com.chatter.core.ChatterService;
import com.chatter.listener.api.EventInfo;
import com.chatter.listener.api.EventManager;

import javafx.scene.layout.Pane;

@Component
public class RegistrationService implements ChatterService {

	private EventManager eventManager;

	private MainViewService mainViewService;

	@Autowired
	public RegistrationService(EventManager eventManager, MainViewService mainViewService) {
		this.mainViewService = mainViewService;
		this.eventManager = eventManager;
		RegistrationController controller = new RegistrationController(this);
		eventManager.registerListener(controller);
	}

	public void showPaneInMainScene(Pane pane) {
		mainViewService.show(pane);
	}

	public void sendEvent(EventInfo eventInfo) {
		eventManager.sendEvent(eventInfo);
	}
}
