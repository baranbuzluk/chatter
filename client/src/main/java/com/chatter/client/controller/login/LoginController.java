package com.chatter.client.controller.login;

import com.chatter.client.enums.ClientEvent;
import com.chatter.client.enums.ClientEventProperties;
import com.chatter.core.AbstractController;
import com.chatter.core.util.JavaFXUtils;
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
	@FXML
	private Button btnSignUp;

	public LoginController(LoginService service) {
		super("Login.fxml", service);
	}

	@FXML
	private void initialize() {
		btnLogin.setOnMouseClicked(e -> executeLoginOperations());
		btnLogin.setOnKeyPressed(e -> doLoginOperations(e));
		txtPassword.setOnKeyPressed(e -> doLoginOperations(e));
		txtUsername.setOnKeyPressed(e -> doLoginOperations(e));
		btnSignUp.setOnMouseClicked(e -> executeSignUpOperations());
		btnSignUp.setOnKeyPressed(e -> doSignUpOperations(e));

	}

	private void executeSignUpOperations() {
		EventInfo event = new EventInfo(ClientEvent.REGISTER);
		service.sendEvent(event);

	}

	private void executeLoginOperations() {
		Account account = LoginHelper.getAccountFromFields(txtUsername, txtPassword);
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

	private void doLoginOperations(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			executeLoginOperations();
		}

	}

	private void doSignUpOperations(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			executeSignUpOperations();
		}

	}
}
