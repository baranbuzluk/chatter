package com.chatter.client.controller.util;

import org.springframework.util.StringUtils;

import com.chatter.data.entity.Account;

import javafx.scene.control.TextField;

public final class AccountUtils {

	private AccountUtils() {
	}

	public static Account createAccountFromFields(TextField usernameTextField, TextField passwordTextField) {
		String username = getOnlyText(usernameTextField.getText());
		String password = getOnlyText(passwordTextField.getText());
		return new Account(username, password);
	}

	private static String getOnlyText(String text) {
		if (StringUtils.hasText(text)) {
			return text;
		}
		return "";
	}
}
