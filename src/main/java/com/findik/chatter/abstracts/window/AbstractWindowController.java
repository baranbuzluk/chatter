package com.findik.chatter.abstracts.window;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

/**
 *
 * @param <T> FXML'deki root Pane'nin tipi. (Ornegin StackPane, VBox vb.)
 */
public abstract class AbstractWindowController implements IWindowController {

	@FXML
	protected StackPane rootPane;

	protected AbstractWindowController(String fxmlName) {
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
		initController();
	}

	protected abstract void initController();

	@Override
	public StackPane getPane() {
		return rootPane;
	}

}
