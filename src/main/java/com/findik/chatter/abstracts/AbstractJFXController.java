package com.findik.chatter.abstracts;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

/**
 *
 * @param <T> FXML'deki root Pane'nin tipi. (Ornegin StackPane, VBox vb.)
 */
public abstract class AbstractJFXController {

	@FXML
	protected StackPane rootPane;

	protected AbstractJFXController(String fxmlName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
			loader.setController(this);
			loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public final void initialize() {
		afterControllerLoaded();
	}

	protected void afterControllerLoaded() {

	}

	public final StackPane getPane() {
		return rootPane;
	}

}
