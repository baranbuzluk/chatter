package com.chatter.client.controller.login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginControllerUtils {

	public LoginControllerUtils() {

	}

	static File file = new File("account_Information.txt");

	public static void readDataFile(TextField usernameTextField, PasswordField passwordTextField) throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			String username = bufferedReader.readLine().toString();
			String passWord = bufferedReader.readLine().toString();
			usernameTextField.setText(username);
			passwordTextField.setText(passWord);
		}
	}

	public static void writeDataFile(String username, String password) throws IOException {

		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
		bufferedWriter.write(username);
		bufferedWriter.write("\n" + password);
		bufferedWriter.close();
	}

	public static void deleteFile() throws IOException {

		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
		Writer.nullWriter();
		bufferedWriter.close();
		file.delete();

	}

	public static void main(String[] args) throws IOException {

	}
}
