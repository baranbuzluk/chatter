package com.chatter.view.controller;

import java.net.URL;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

final class JavaFXUtils {
	private static Logger LOGGER = LoggerFactory.getLogger(JavaFXUtils.class);

	private JavaFXUtils() {
	}

	public static void showAlertMessage(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public static void loadFXMLWithRoot(Object controller, String fxmlName) {
		if (controller == null || fxmlName == null || fxmlName.isBlank()) {
			return;
		}

		Class<?> clazz = controller.getClass();
		try {
			URL resource = clazz.getClassLoader().getResource(fxmlName);
			FXMLLoader loader = new FXMLLoader(resource);
			loader.setController(controller);
			loader.setRoot(controller);
			loader.load();
			String message = MessageFormat.format("Loaded {0} to {1}", fxmlName, clazz);
			LOGGER.info(message);
		} catch (Exception e) {
			String message = MessageFormat.format("{0} could not be loaded to {1}", fxmlName, clazz);
			LOGGER.error(message, e);
		}
	}
}
