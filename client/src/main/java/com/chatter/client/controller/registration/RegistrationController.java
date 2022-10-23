package com.chatter.client.controller.registration;

import com.chatter.client.enums.ClientEvent;
import com.chatter.core.abstracts.AbstractController;
import com.chatter.event.api.ChatterEventListener;
import com.chatter.event.api.EventInfo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
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
	@FXML
	private Button buttonBack;

	public RegistrationController(RegistrationService service) {
		super("Registration.fxml", service);
	}

	@FXML
	private void initialize() {

		usernameTextField.setOnKeyPressed(e -> doRegistrationOperations(e));
		passwordTextField.setOnKeyPressed(e -> doRegistrationOperations(e));
		retypePasswordTextField.setOnKeyPressed(e -> doRegistrationOperations(e));
		registerButton.setOnKeyPressed(e -> doRegistrationOperations(e));
		registerButton.setOnMouseClicked(e -> executeRegistrationOperations());

	}

	private void executeRegistrationOperations() {

		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		boolean isEmptyUsernameOrPassword = username.isEmpty() || password.isEmpty();

		if (isEmptyUsernameOrPassword) {
			RegistrationControllerUtils.showUsernameOrPasswordEmptyAlertMessage();
			return;
		}

		boolean hasUsername = service.existsByUsername(username);
		if (hasUsername) {
			RegistrationControllerUtils.showUsernameAlreadyExistsAlertMessage();
			return;
		}

		String retypePassword = retypePasswordTextField.getText();
		boolean isEqualsPassword = password.equals(retypePassword);
		if (!isEqualsPassword) {
			RegistrationControllerUtils.showReEnterPasswordsAlertMessage();
			return;
		}

		boolean isRegistered = service.registerAccount(username, password);
		if (!isRegistered) {
			RegistrationControllerUtils.showRegistrationFailedAlertMessage();
		}

	}

	private void doRegistrationOperations(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			executeRegistrationOperations();
		}
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ClientEvent.REGISTER) {
			Platform.runLater(() -> service.showPaneInMainScene(getPane()));
		}

	}

	@FXML
	void buttonBackOnAction(ActionEvent event) {
		service.sendEvent(new EventInfo(ClientEvent.STARTED_APPLICATION));
	}

}
