package com.chatter.core.abstracts;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chatter.core.util.JavaFXUtils;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public abstract class AbstractController<T extends ChatterService> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@FXML
	protected Pane rootPane;

	protected T service;

	protected AbstractController(String fxmlName, T service) {
		this.service = Objects.requireNonNull(service, "service can not be null!");
		JavaFXUtils.loadFXML(this, fxmlName);
	}

	public Pane getPane() {
		return rootPane;
	}

}
