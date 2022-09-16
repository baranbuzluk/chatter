package com.chatter.client.controller.login;

import java.io.File;
import java.util.prefs.Preferences;

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
	private Preferences preferences;
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
		if (checkUsernameAndPassword) {
			replaceUsernameAndPassword(username, password);
		} else {
			String header = "Username or password is incorrect ";
			String content = "Please enter correct username or password";
			String title = "Failed login";
			JavaFXUtils.showAlertMessage(AlertType.ERROR, title, header, content);
		}
	}

	private void replaceUsernameAndPassword(String username, String password) {
		boolean isSelected = checkBoxRememberMe.isSelected();
		if (isSelected) {
			preferences.put("textFieldUsername", username);
			preferences.put("textFieldPassword", password);
		} else {
			preferences.put("textFieldUsername", "");
			preferences.put("textFieldPassword", "");
		}
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ClientEvent.STARTED_APPLICATION) {
			Platform.runLater(() -> service.showInMainWindow(rootPane));

		}
		preferences = Preferences.userNodeForPackage(LoginController.class);
		if (preferences.get("textFieldUsername", null) != null
				&& (preferences.get("textFieldPassword", null) != null)) {
			textFieldUsername.setText(preferences.get("textFieldUsername", null));
			textFieldPassword.setText(preferences.get("textFieldPassword", null));

		}

	}
}
