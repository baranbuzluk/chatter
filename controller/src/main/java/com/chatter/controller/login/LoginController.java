package com.chatter.controller.login;

import static com.chatter.event.data.ChatterEvent.LOGGED_IN_ACCOUNT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.controller.session.ChatterSession;
import com.chatter.data.entity.Account;
import com.chatter.event.data.ChatterEvent;
import com.chatter.event.data.ChatterEventProperties;
import com.chatter.event.data.EventInfo;
import com.chatter.view.service.AbstractController;
import com.chatter.view.service.CommonService;
import com.chatter.view.service.JavaFXUtils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

@Component
public class LoginController extends AbstractController {

	@FXML
	private Button buttonLogin;

	@FXML
	private PasswordField textFieldPassword;

	@FXML
	private Button buttonSignUp;

	@FXML
	private TextField textFieldUsername;

	@Autowired
	public LoginController(CommonService commonService) {
		super("Login.fxml", commonService);
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
		commonService.sendEvent(new EventInfo(ChatterEvent.REGISTER));
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
		Account account = commonService.getAccountByUsernameAndPassword(username, password);
		if (account == null) {
			String header = "Username or password is incorrect ";
			String content = "Please enter correct username or password";
			String title = "Failed login";
			JavaFXUtils.showAlertMessage(AlertType.ERROR, title, header, content);
		} else {
			EventInfo event = new EventInfo(ChatterEvent.LOGGED_IN_ACCOUNT);
			ChatterSession.getInstance().openSession(account);
			event.put(ChatterEventProperties.ACCOUNT, account);
			commonService.sendEvent(event);
		}

	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		ChatterEvent event = eventInfo.getEvent();
		if (event == ChatterEvent.STARTED_APPLICATION) {
			Platform.runLater(() -> commonService.showInMainWindow(getRootPane()));
		}
	}
}
