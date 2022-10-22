package com.chatter.client.controller.login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.chatter.client.enums.ClientEvent;
import com.chatter.core.abstracts.AbstractController;
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
	private TextField textFieldUsername;

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
		String username = textFieldUsername.getText();
		String password = textFieldPassword.getText();
		boolean checkUsernameAndPassword = service.login(username, password);
		if (!checkUsernameAndPassword) {
			String header = "Username or password is incorrect ";
			String content = "Please enter correct username or password";
			String title = "Failed login";
			JavaFXUtils.showAlertMessage(AlertType.ERROR, title, header, content);
		}
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ClientEvent.STARTED_APPLICATION) {
			Platform.runLater(() -> service.showInMainWindow(rootPane));

		}
		if (file.isFile()) {
			try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
				String username = bufferedReader.readLine();
				String password = bufferedReader.readLine();

				textFieldUsername.setText(username);
				textFieldPassword.setText(password);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
