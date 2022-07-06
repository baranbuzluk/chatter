package com.chatter.client.controller.login;

import com.chatter.client.enums.ClientEvent;
import com.chatter.client.enums.ClientEventProperties;
import com.chatter.core.AbstractController;
import com.chatter.core.util.JavaFXUtils;
import com.chatter.data.entity.Account;
import com.chatter.listener.api.EventInfo;
import com.chatter.listener.api.EventListener;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController extends AbstractController<LoginService> implements EventListener {

	@FXML
	private Button loginButton;

	@FXML
	private PasswordField passwordTextField;

	@FXML
	private Button signUpButton;

	@FXML
	private TextField usernameTextField;

	public LoginController(LoginService service) {
		super("Login.fxml", service);
	}

	@FXML
	private void initialize() {
		initLoginEventHandler();
		initSignUpEventHandler();
	}

	private void initSignUpEventHandler() {
		signUpButton.setOnMouseClicked(e -> service.sendEvent(new EventInfo(ClientEvent.REGISTER)));

		signUpButton.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				service.sendEvent(new EventInfo(ClientEvent.REGISTER));
		});
	}

	private void initLoginEventHandler() {
		loginButton.setOnMouseClicked(e -> executeLoginOperations());

		EventHandler<KeyEvent> loginEnterKeyEventHandler = e -> {
			if (e.getCode() == KeyCode.ENTER)
				executeLoginOperations();
		};

		loginButton.setOnKeyPressed(loginEnterKeyEventHandler);
		passwordTextField.setOnKeyPressed(loginEnterKeyEventHandler);
		usernameTextField.setOnKeyPressed(loginEnterKeyEventHandler);
	}

	private void executeLoginOperations() {
		Account accountFromFields = LoginHelper.createAccountFromFields(usernameTextField, passwordTextField);
		if (service.checkAccount(accountFromFields)) {
			EventInfo event = new EventInfo(ClientEvent.LOGGED_IN_ACCOUNT);
			event.put(ClientEventProperties.ACCOUNT, accountFromFields);
			service.sendEvent(event);
		} else {
			String header = "Username or password is incorrect ";
			String content = "Please enter correct username or password";
			String title = "Failed login";
			JavaFXUtils.showAlertMessage(AlertType.ERROR, title, header, content);
		}
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ClientEvent.STARTED_APPLICATION) {
			Platform.runLater(() -> service.showInMainWindow(rootPane));
		}
	}

}
