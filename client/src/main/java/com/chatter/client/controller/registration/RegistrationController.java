package com.chatter.client.controller.registration;

import com.chatter.client.enums.ClientEvent;
import com.chatter.core.AbstractController;
import com.chatter.listener.api.EventInfo;
import com.chatter.listener.api.EventListener;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegistrationController extends AbstractController<RegistrationService> implements EventListener {

	@FXML
	private TextField passwordTextField;

	@FXML
	private Button registerButton;

	@FXML
	private TextField retypePasswordTextField;

	@FXML
	private TextField usernameTextField;

	public RegistrationController(RegistrationService service) {
		super("Registration.fxml", service);
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		// FIXME: burakcantemur tarafından düzeltilecektir.
		if (eventInfo.getEvent() == ClientEvent.LOGGED_IN_ACCOUNT) {
			Platform.runLater(() -> service.showPaneInMainScene(getPane()));
		}

	}

}
