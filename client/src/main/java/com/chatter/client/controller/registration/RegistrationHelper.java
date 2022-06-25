package com.chatter.client.controller.registration;

import com.chatter.data.entity.Account;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public final class RegistrationHelper {
	public RegistrationHelper() {

	}

	public static Account getAccountFromFields(TextField usernameTextField, PasswordField passwordTextField) {
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		return new Account(username, password);

	}
}
