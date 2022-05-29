package com.chatter.core;

import java.util.Objects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public abstract class AbstractController<T extends ChatterService> {

	@FXML
	protected Pane rootPane;

	protected T service;

	protected AbstractController(String fxmlName, T service) {
		try {
			this.service = Objects.requireNonNull(service, "service can not be null!");
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
			loader.setController(this);
			loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Pane getPane() {
		return rootPane;
	}

}
