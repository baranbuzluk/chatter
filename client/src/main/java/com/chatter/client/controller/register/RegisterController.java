package com.chatter.client.controller.register;

import com.chatter.client.enums.ClientEvent;
import com.chatter.core.AbstractController;
import com.chatter.listener.api.EventInfo;
import com.chatter.listener.api.EventListener;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController extends AbstractController<RegisterService> implements EventListener {
	@FXML
	private TextField txtUsername;

	@FXML
	private PasswordField txtRetypePassword;

	@FXML
	private PasswordField txtPassword;
	@FXML
	private Button btnRegistration;

	public RegisterController(RegisterService service) {
		super("Register.fxml", service);
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ClientEvent.REGISTER) {
			Platform.runLater(() -> service.showInMainWindow(rootPane));

		}
	}

}
