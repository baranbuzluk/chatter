package com.chatter.view.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chatter.event.api.ChatterEventListener;

import javafx.fxml.FXML;
import javafx.scene.Parent;

public abstract class AbstractController implements ChatterEventListener {

	@FXML
	protected Parent rootPane;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected CommonService commonService;

	private String fxmlName;

	protected AbstractController(String fxmlName, CommonService commonService) {
		this.fxmlName = fxmlName;
		this.commonService = commonService;
	}

	public Parent getRootPane() {
		if (rootPane == null) {
			JavaFXUtils.loadFXML(this, fxmlName);
		}
		return rootPane;
	}

}
