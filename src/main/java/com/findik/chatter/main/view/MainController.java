package com.findik.chatter.main.view;

import com.findik.chatter.abstracts.window.AbstractWindowController;

import javafx.application.Platform;
import javafx.scene.layout.Pane;

public class MainController extends AbstractWindowController {

	public MainController() {
		super("Main.fxml");
	}

	@Override
	protected void initController() {

	}

	public void setInnerPane(Pane innerPane) {
		if (innerPane == null) {
			throw new IllegalArgumentException("Not null InnerPane");
		}

		Platform.runLater(() -> {
			removeInnerPane();
			getPane().getChildren().add(innerPane);
		});

	}

	public void removeInnerPane() {
		getPane().getChildren().clear();
	}

}
