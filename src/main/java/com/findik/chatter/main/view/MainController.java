package com.findik.chatter.main.view;

import com.findik.chatter.abstracts.window.AbstractWindowController;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainController extends AbstractWindowController<StackPane> {

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

		removeInnerPane();
		getPane().getChildren().add(innerPane);
	}

	public void removeInnerPane() {
		getPane().getChildren().clear();
	}

}
