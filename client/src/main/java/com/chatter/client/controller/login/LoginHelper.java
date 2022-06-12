package com.chatter.client.controller.login;

import org.springframework.util.StringUtils;

import com.chatter.data.entity.Account;

import javafx.scene.control.TextField;

public final class LoginHelper {

	private LoginHelper() {
	}

	public static Account getAccountFromFields(TextField usernameTextField, TextField passwordTextField) {
		String username = getOnlyText(usernameTextField.getText());
		String password = getOnlyText(passwordTextField.getText());
		return new Account(username, password);
	}

	public static String getOnlyText(String text) {
		if (StringUtils.hasText(text)) {
			return text;
		}
		return "";
	}
}
