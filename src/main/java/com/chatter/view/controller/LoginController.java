package com.chatter.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.event.ChatterEvent;
import com.chatter.event.ChatterEventListener;
import com.chatter.event.EventInfo;
import com.chatter.event.EventService;
import com.chatter.service.LoginService;
import com.chatter.view.ViewService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

@Component
public class LoginController extends StackPane implements ChatterEventListener {

	@FXML
	private Button buttonLogin;

	@FXML
	private PasswordField textFieldPassword;

	@FXML
	private Button buttonSignUp;

	@FXML
	private TextField textFieldUsername;

	private LoginService loginService;

	private EventService eventService;

	private ViewService viewService;

	@Autowired
	public LoginController(LoginService loginService, EventService eventService, ViewService viewService) {
		this.loginService = loginService;
		this.eventService = eventService;
		this.viewService = viewService;
		JavaFXUtils.loadFXMLWithRoot(this, "Login.fxml");
	}

	@FXML
	void loginButtonOnAction(ActionEvent event) {
		String username = textFieldUsername.getText();
		String password = textFieldPassword.getText();
		boolean login = loginService.login(username, password);
		if (login) {
			eventService.sendEvent(new EventInfo(ChatterEvent.OPEN_CHAT_VIEW));
		} else {
			AlertMessage.showUsernameOrPasswordIsWrong();
		}
	}

	@FXML
	void rootPaneOnKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			loginButtonOnAction(null);
		}
	}

	@FXML
	void signUpButtonOnAction(ActionEvent event) {
		eventService.sendEvent(new EventInfo(ChatterEvent.OPEN_REGISTRATION_VIEW));
	}

	@FXML
	void signUpButtonOnKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			signUpButtonOnAction(null);
		}
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.event == ChatterEvent.OPEN_LOGIN_VIEW) {
			viewService.show(this);
		}
	}
}
