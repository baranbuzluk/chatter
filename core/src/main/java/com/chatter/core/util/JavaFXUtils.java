package com.chatter.core.util;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public final class JavaFXUtils {
	private JavaFXUtils() {
	}

	public static void showAlertMessage(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public static void loadFXML(Object controller, String fxmlName) throws IOException {
		if (controller == null)
			return;

		URL resource = controller.getClass().getResource(fxmlName);
		FXMLLoader loader = new FXMLLoader(resource);
		loader.setController(controller);
		loader.load();
	}
}
