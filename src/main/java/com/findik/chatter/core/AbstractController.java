package com.findik.chatter.core;

import java.io.IOException;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public abstract class AbstractController<T extends ChatterService> {

	@FXML
	protected Pane rootPane;

	protected T service;

	protected AbstractController(String fxmlName, T service) throws IOException {
		this.service = Objects.requireNonNull(service);
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlName));
		loader.setController(this);
		loader.load();
	}

	public Pane getPane() {
		return rootPane;
	}

}
