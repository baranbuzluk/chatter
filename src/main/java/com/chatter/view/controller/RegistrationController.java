package com.chatter.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.dto.AccountDto;
import com.chatter.event.ChatterEvent;
import com.chatter.event.ChatterEventListener;
import com.chatter.event.EventInfo;
import com.chatter.event.EventService;
import com.chatter.service.RegistrationService;
import com.chatter.view.ViewService;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

@Component
public class RegistrationController extends StackPane implements ChatterEventListener {

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

	private RegistrationService registrationService;

	private EventService eventService;

	private ViewService viewService;

	@Autowired
	public RegistrationController(RegistrationService registrationService, EventService eventService,
			ViewService viewService) {
		this.registrationService = registrationService;
		this.eventService = eventService;
		this.viewService = viewService;
		JavaFXUtils.loadFXMLWithRoot(this, "Registration.fxml");
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
			AlertMessage.showUsernameOrPasswordEmptyAlertMessage();
			return;
		}

		boolean hasUsername = registrationService.existsUsername(username);
		if (hasUsername) {
			AlertMessage.showUsernameAlreadyExistsAlertMessage();
			return;
		}

		String retypePassword = retypePasswordTextField.getText();
		boolean isEqualsPassword = password.equals(retypePassword);
		if (!isEqualsPassword) {
			AlertMessage.showReEnterPasswordsAlertMessage();
			return;
		}

		AccountDto account = new AccountDto(username, password);
		boolean registerAccount = registrationService.registerAccount(account);
		if (registerAccount) {
			eventService.sendEvent(new EventInfo(ChatterEvent.OPEN_LOGIN_VIEW));
		} else {
			AlertMessage.showRegistrationFailedAlertMessage();
		}

	}

	private void doRegistrationOperations(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			executeRegistrationOperations();
		}
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.event == ChatterEvent.OPEN_REGISTRATION_VIEW) {
			Platform.runLater(() -> viewService.show(this));
		}
	}

	@FXML
	void buttonBackOnAction(ActionEvent event) {
		eventService.sendEvent(new EventInfo(ChatterEvent.OPEN_LOGIN_VIEW));
	}

}
