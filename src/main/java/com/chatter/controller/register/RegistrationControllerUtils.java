package com.chatter.controller.register;

import com.chatter.service.JavaFXUtils;

import javafx.scene.control.Alert.AlertType;

public final class RegistrationControllerUtils {

	public static void showUsernameOrPasswordEmptyAlertMessage() {

		String title = "Failed Sign Up";
		String header = "Your  username or password cannot be empty";
		String content = "Please enter username or password";
		JavaFXUtils.showAlertMessage(AlertType.ERROR, title, header, content);
	}

	public static void showReEnterPasswordsAlertMessage() {
		String title = "Failed Sign Up";
		String header = "Your passwords do not match";
		String content = "Please re-enter passwords";
		JavaFXUtils.showAlertMessage(AlertType.ERROR, title, header, content);
	}

	public static void showSuccessfulSignUpAlertMessage() {
		String title = "Successful Sign Up";
		String header = "You have successfully registered";
		String content = "";
		JavaFXUtils.showAlertMessage(AlertType.INFORMATION, title, header, content);
	}

	public static void showUsernameAlreadyExistsAlertMessage() {
		String title = "Failed Sign Up";
		String header = "Username already exists";
		String content = "";
		JavaFXUtils.showAlertMessage(AlertType.INFORMATION, title, header, content);

	}

	public static void showRegistrationFailedAlertMessage() {
		String title = "Failed Sign Up";
		String header = "Registration Failed";
		String content = " ";
		JavaFXUtils.showAlertMessage(AlertType.ERROR, title, header, content);
	}
}
