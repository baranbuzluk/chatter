package com.chatter.client.controller.registration;

import com.chatter.client.controller.util.AccountUtils;
import com.chatter.client.enums.ClientEvent;
import com.chatter.core.AbstractController;
import com.chatter.data.entity.Account;
import com.chatter.listener.api.ChatterEventListener;
import com.chatter.listener.api.EventInfo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class RegistrationController extends AbstractController<RegistrationService> implements ChatterEventListener {
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
		registerButton.setOnMouseClicked(e -> executeregistrationOperations());

	}

	private void executeregistrationOperations() {
		String password = passwordTextField.getText();
		String retypePassword = retypePasswordTextField.getText();

		boolean isEmptyUsernameOrPassword = usernameTextField.getText().isEmpty()
				|| passwordTextField.getText().isEmpty();

		boolean isAccountWithTheSameUsername = service.getByUsername(usernameTextField.getText()) != null;

//		boolean isPasswordValidation = !password.equals(retypePassword);

		boolean isPasswordMatching = password.equals(retypePassword);

		if (isEmptyUsernameOrPassword) {
			RegistrationControllerUtils.showAlertMessageRequestUsernameOrPasswordEntry();

		} else if (!isPasswordMatching) {
			RegistrationControllerUtils.showAlertMessageReEnterPasswords();

		} else if (isAccountWithTheSameUsername) {

			RegistrationControllerUtils.showAlertMessageUsernameAlreadyExists();
		} else {
			registerAccount();
			RegistrationControllerUtils.showAlertMessageSuccessfulSignUp();
			EventInfo event = new EventInfo(ClientEvent.STARTED_APPLICATION);
			service.sendEvent(event);
		}
	}

	private void doRegistrationOperations(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			executeregistrationOperations();
		}
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ClientEvent.REGISTER) {
			Platform.runLater(() -> service.showPaneInMainScene(getPane()));
		}

	}

	private boolean registerAccount() {
		try {
			Account account = AccountUtils.createAccountFromFields(usernameTextField, passwordTextField);
			service.registerAccount(account);
			return true;
		} catch (Exception e) {
			// Exception will not be handled
		}
		return false;
	}
}
