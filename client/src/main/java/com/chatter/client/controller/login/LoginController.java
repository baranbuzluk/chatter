package com.chatter.client.controller.login;

import java.text.MessageFormat;

import com.chatter.client.controller.util.AccountUtils;
import com.chatter.client.enums.ClientEvent;
import com.chatter.client.enums.ClientEventProperties;
import com.chatter.core.abstracts.AbstractController;
import com.chatter.core.entity.Account;
import com.chatter.core.event.listener.ChatterEventListener;
import com.chatter.core.event.listener.EventInfo;
import com.chatter.core.util.JavaFXUtils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController extends AbstractController<LoginService> implements ChatterEventListener {

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
	void loginButtonOnAction(ActionEvent event) {
		executeLoginOperations();
	}

	@FXML
	void rootPaneOnKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			loginButtonOnAction(null);
		}
	}

	@FXML
	void signUpButtonOnAction(ActionEvent event) {
		service.sendEvent(new EventInfo(ClientEvent.REGISTER));
	}

	@FXML
	void signUpButtonOnKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			signUpButtonOnAction(null);
		}
	}

	private void executeLoginOperations() {
		Account accountFromFields = AccountUtils.createAccountFromFields(usernameTextField, passwordTextField);
		if (service.checkAccount(accountFromFields)) {
			String log = MessageFormat.format("{0} logged-in.", accountFromFields.getUsername());
			logger.info(log);
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
