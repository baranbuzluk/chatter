package com.chatter.client.controller.registration;

import com.chatter.client.enums.ClientEvent;
import com.chatter.core.AbstractController;
import com.chatter.core.util.JavaFXUtils;
import com.chatter.data.entity.Account;
import com.chatter.listener.api.EventInfo;
import com.chatter.listener.api.EventListener;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class RegistrationController extends AbstractController<RegistrationService> implements EventListener {
	@FXML
	private TextField usernameTextField;

	@FXML
	private PasswordField passwordTextField;

	@FXML
	private PasswordField retypePasswordTextField;

	@FXML
	private Button registerButton;

	public RegistrationController(RegistrationService service) {
		super("Registration.fxml", service);
	}

	@FXML
	private void initialize() {

		usernameTextField.setOnKeyPressed(e -> doRegistrationOperations(e));
		passwordTextField.setOnKeyPressed(e -> doRegistrationOperations(e));
		retypePasswordTextField.setOnKeyPressed(e -> doRegistrationOperations(e));
		registerButton.setOnKeyPressed(e -> doRegistrationOperations(e));
		registerButton
				.setOnMouseClicked(e -> executeregistrationOperations(passwordTextField, retypePasswordTextField));

	}

	private void executeregistrationOperations(PasswordField passwordTextField, PasswordField retypePasswordTextField) {
		String password = passwordTextField.getText();
		String retypePassword = retypePasswordTextField.getText();
		if (usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
			String title = "Failed Sign Up";
			String header = "Your  username or password cannot be empty";
			String content = "Please enter username or password";
			JavaFXUtils.showAlertMessage(AlertType.ERROR, title, header, content);

		} else if (!password.equals(retypePassword)) {

			String title = "Failed Sign Up";
			String header = "Your passwords do not match";
			String content = "Please re-enter passwords";
			JavaFXUtils.showAlertMessage(AlertType.ERROR, title, header, content);
		} else {
			registerAccount();
			String title = "Successful Sign Up";
			String header = "You have successfully registered";
			String content = "";
			JavaFXUtils.showAlertMessage(AlertType.INFORMATION, title, header, content);
			EventInfo event = new EventInfo(ClientEvent.STARTED_APPLICATION);
			service.sendEvent(event);
		}

	}

	private void doRegistrationOperations(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			executeregistrationOperations(passwordTextField, retypePasswordTextField);
		}
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ClientEvent.REGISTER) {
			Platform.runLater(() -> service.showPaneInMainScene(getPane()));
		}

	}

	private void registerAccount() {
		Account account = RegistrationHelper.getAccountFromFields(usernameTextField, passwordTextField);
		service.registerAccount(account);
	}
}
