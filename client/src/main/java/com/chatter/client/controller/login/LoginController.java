package com.chatter.client.controller.login;

import java.io.IOException;

import org.springframework.util.StringUtils;

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

	public LoginController(LoginService service) throws IOException {
		super("Login.fxml", service);
	}

	@FXML
	private void initialize() {
		btnLogin.setOnMouseClicked(e -> {
			Account account = getAccount();
			if (validateAccount(account)) {
				executeLoginOperations(account);
			}
		});
	}

	private void executeLoginOperations(Account account) {
		String username = account.getUsername();
		Account accountFromDb = service.getByUsername(username);
		if (account.equals(accountFromDb)) {
			EventInfo event = new EventInfo(ClientEvent.LOGGED_IN_ACCOUNT);
			event.put(ClientEventProperties.ACCOUNT, accountFromDb);
			service.sendEvent(event);
		}
	}

	private boolean validateAccount(Account account) {
		String username = account.getUsername();
		String password = account.getPassword();
		boolean hasUsername = username != null && StringUtils.hasText(username);
		boolean hasPassword = password != null && StringUtils.hasText(password);
		return hasUsername && hasPassword;
	}

	public Account getAccount() {
		String username = getOnlyText(txtUsername.getText());
		String password = getOnlyText(txtPassword.getText());
		return new Account(username, password);
	}

	private String getOnlyText(String text) {
		if (StringUtils.hasText(text)) {
			return text;
		}
		return null;
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ClientEvent.STARTED_APPLICATION) {
			Platform.runLater(() -> service.showInMainWindow(rootPane));
		}
	}

}
