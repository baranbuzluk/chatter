package com.chatter.client.controller.login;

import com.chatter.client.enums.ClientEvent;
import com.chatter.client.enums.ClientEventProperties;
import com.chatter.client.utils.JavaFXUtils;
import com.chatter.core.AbstractController;
import com.chatter.data.entity.Account;
import com.chatter.listener.api.EventInfo;
import com.chatter.listener.api.EventListener;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController extends AbstractController<LoginService> implements EventListener {

	@FXML
	private Button btnLogin;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private TextField txtUsername;

	public LoginController(LoginService service) {
		super("Login.fxml", service);
	}

	@FXML
	private void initialize() {
		btnLogin.setOnMouseClicked(e -> executeLoginOperations());
		txtPassword.setOnKeyPressed(e -> txtPasswordKeyPressed(e));
		txtUsername.setOnKeyPressed(e -> txtUsernameKeyPressed(e));
	}

	private void executeLoginOperations() {
		alertMessage();
		Account account = LoginHelper.getAccountFromFields(txtUsername, txtPassword);
		if (!LoginHelper.validateAccount(account))
			return;

		String username = account.getUsername();
		Account accountFromDb = service.getByUsername(username);
		if (account.equals(accountFromDb)) {
			sendLoginInEvent(accountFromDb);
		} else {
			String header = "Username or password is incorrect ";
			String content = "Please enter correct username or password";
			String title = "Failed login";
			JavaFXUtils.showAlertMessage(AlertType.ERROR, title, header, content);
		}
	}

	private void sendLoginInEvent(Account accountFromDb) {
		EventInfo event = new EventInfo(ClientEvent.LOGGED_IN_ACCOUNT);
		event.put(ClientEventProperties.ACCOUNT, accountFromDb);
		service.sendEvent(event);
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ClientEvent.STARTED_APPLICATION) {
			Platform.runLater(() -> service.showInMainWindow(rootPane));
		}
	}

	public void alertMessage() {
		String username = txtUsername.getText();
		String password = txtPassword.getText();
		String title = "Incorrect Value";
		if (username.isEmpty() && password.isEmpty()) {
			String header = "Username and password cannot be empty";
			String content = "Please enter your username and password";
			JavaFXUtils.showAlertMessage(AlertType.INFORMATION, title, header, content);
		} else if (username.isEmpty()) {
			String header = "Username can not be empty";
			String content = "Please enter your username";
			JavaFXUtils.showAlertMessage(AlertType.INFORMATION, title, header, content);
		} else if (password.isEmpty()) {
			String header = "Password can not be empty";
			String content = "Please enter your password";
			JavaFXUtils.showAlertMessage(AlertType.INFORMATION, title, header, content);
		}
	}

	private void txtPasswordKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			executeLoginOperations();
		}
	}

	private void txtUsernameKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			executeLoginOperations();
		}
	}

}
