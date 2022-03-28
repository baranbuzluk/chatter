package com.findik.chatter.abstracts;

import javafx.scene.layout.StackPane;

public abstract class AbstractGuiWindow<T extends AbstractJFXController> {

	protected T controller;

	protected abstract T createController();

	protected void afterControllerLoaded() {

	}

	public final StackPane getRootPane() {
		if (controller == null) {
			controller = createController();
			afterControllerLoaded();
		}
		return controller.getPane();
	}

}
