package com.chatter.client.controller.login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;

import com.chatter.client.controller.util.AccountUtils;
import com.chatter.client.enums.ClientEvent;
import com.chatter.client.enums.ClientEventProperties;
import com.chatter.core.abstracts.AbstractController;
import com.chatter.core.entity.Account;
import com.chatter.core.event.listener.ChatterEventListener;
import com.chatter.core.event.listener.EventInfo;
import com.chatter.core.util.JavaFXUtils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController extends AbstractController<LoginService> implements ChatterEventListener {

	@FXML
	private Button buttonLogin;

	@FXML
	private PasswordField textFieldPassword;

	@FXML
	private Button buttonSignUp;

	@FXML
	private TextField testFieldUsername;
	@FXML
	private CheckBox checkBoxRememberMe;
	File file = new File("account_Information.txt");

	public LoginController(LoginService service) {
		super("Login.fxml", service);

	}

	@FXML
	void loginButtonOnAction(ActionEvent event) {
		executeLoginOperations();
	}

	@FXML
	void rootPaneOnKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			loginButtonOnAction(null);
		}
	}

	@FXML
	void signUpButtonOnAction(ActionEvent event) {
		service.sendEvent(new EventInfo(ClientEvent.REGISTER));
	}

	@FXML
	void signUpButtonOnKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			signUpButtonOnAction(null);
		}
	}

	private void executeLoginOperations() {
		Account accountFromFields = AccountUtils.createAccountFromFields(testFieldUsername, textFieldPassword);

		if (service.checkAccount(accountFromFields)) {
			String log = MessageFormat.format("{0} logged-in.", accountFromFields.getUsername());
			logger.info(log);

			executeRememberMeOperations(accountFromFields);
			EventInfo event = new EventInfo(ClientEvent.LOGGED_IN_ACCOUNT);
			event.put(ClientEventProperties.ACCOUNT, accountFromFields);
			service.sendEvent(event);
		} else {
			String header = "Username or password is incorrect ";
			String content = "Please enter correct username or password";
			String title = "Failed login";
			JavaFXUtils.showAlertMessage(AlertType.ERROR, title, header, content);
		}
	}

	private void executeRememberMeOperations(Account account) {
		boolean isSelected = checkBoxRememberMe.isSelected();
		if (isSelected) {

			try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
				bufferedWriter.write(account.getUsername());
				bufferedWriter.write("\n" + account.getPassword());

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			file.delete();

		}
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ClientEvent.STARTED_APPLICATION) {
			Platform.runLater(() -> service.showInMainWindow(rootPane));

		}
		if (file.isFile()) {
			try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
				String username = bufferedReader.readLine().toString();
				String password = bufferedReader.readLine().toString();

				testFieldUsername.setText(username);
				textFieldPassword.setText(password);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
