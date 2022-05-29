package com.chatter.client.controller.login;

import com.chatter.client.enums.ClientEvent;
import com.chatter.client.enums.ClientEventProperties;
import com.chatter.core.AbstractController;
import com.chatter.data.entity.Account;
import com.chatter.listener.api.EventInfo;
import com.chatter.listener.api.EventListener;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
	}

	private void executeLoginOperations() {
		Account account = LoginHelper.getAccountFromFields(txtUsername, txtPassword);
		if (!LoginHelper.validateAccount(account))
			return;

		String username = account.getUsername();
		Account accountFromDb = service.getByUsername(username);
		if (account.equals(accountFromDb)) {
			sendLoginInEvent(accountFromDb);
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

}
