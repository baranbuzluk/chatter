package com.chatter.client.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public final class JavaFXUtils {
	private JavaFXUtils() {
	}

	public static void showAlertMessage(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
