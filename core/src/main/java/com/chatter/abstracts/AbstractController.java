package com.chatter.abstracts;

import java.util.Objects;

import com.chatter.util.JavaFXUtils;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public abstract class AbstractController<T extends ChatterService> {

	@FXML
	protected Pane rootPane;

	protected T service;

	protected AbstractController(String fxmlName, T service) {
		try {
			this.service = Objects.requireNonNull(service, "service can not be null!");
			JavaFXUtils.loadFXML(this, fxmlName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Pane getPane() {
		return rootPane;
	}

}
