package com.findik.chatter.window.login.view;

import java.util.function.Consumer;

import org.springframework.util.StringUtils;

import com.findik.chatter.abstracts.window.AbstractWindowController;
import com.findik.chatter.entity.Account;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends AbstractWindowController {

	@FXML
	private Button btnLogin;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private TextField txtUsername;

	public LoginController() {
		super("Login.fxml");
	}

	@Override
	protected void initController() {

	}

	public void setLoginButtonOnClickedEvent(Consumer<Account> handle) {
		btnLogin.setOnMouseClicked(e -> {
			Account account = getAccount();
			if (validateAccount(account)) {
				handle.accept(account);
			}
		});
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
