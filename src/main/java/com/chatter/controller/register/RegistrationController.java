package com.chatter.controller.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.data.entity.Account;
import com.chatter.event.ChatterEvent;
import com.chatter.event.EventInfo;
import com.chatter.service.AbstractController;
import com.chatter.service.CommonService;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

@Component
public class RegistrationController extends AbstractController {

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

	@Autowired
	public RegistrationController(CommonService commonService) {
		super("Registration.fxml", commonService);
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

		boolean hasUsername = commonService.existsByUsername(username);
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

		Account account = new Account(username, password);
		Account registeredAccount = commonService.saveAccount(account);
		if (registeredAccount == null) {
			RegistrationControllerUtils.showRegistrationFailedAlertMessage();
		} else {
			RegistrationControllerUtils.showSuccessfulSignUpAlertMessage();
			EventInfo event = new EventInfo(ChatterEvent.STARTED_APPLICATION);
			commonService.sendEvent(event);
		}

	}

	private void doRegistrationOperations(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			executeRegistrationOperations();
		}
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ChatterEvent.REGISTER) {
			Platform.runLater(() -> commonService.showInMainWindow(getRootPane()));
		}

	}

	@FXML
	void buttonBackOnAction(ActionEvent event) {
		commonService.sendEvent(new EventInfo(ChatterEvent.STARTED_APPLICATION));
	}

}
