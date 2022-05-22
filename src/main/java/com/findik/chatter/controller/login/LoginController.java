package com.findik.chatter.controller.login;

import java.io.IOException;

import org.springframework.util.StringUtils;

import com.findik.chatter.core.AbstractController;
import com.findik.chatter.entity.Account;
import com.findik.chatter.enums.ChatterEvent;
import com.findik.chatter.enums.ChatterEventProperties;
import com.findik.chatter.listener.EventInfo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends AbstractController<LoginService> {

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
			EventInfo event = new EventInfo(ChatterEvent.LOGGED_IN_ACCOUNT);
			event.put(ChatterEventProperties.ACCOUNT, accountFromDb);
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

}
