package com.findik.chatter.main.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.findik.chatter.abstracts.IWindow;
import com.findik.chatter.enums.ApplicationConstant;
import com.findik.chatter.main.api.IMainWindowService;
import com.findik.chatter.main.view.MainController;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

@Component(value = ApplicationConstant.MAIN_WINDOW)
public class MainWindowService implements IWindow, IMainWindowService {

	private MainController controller;

	@PostConstruct
	private void postConstruct() {
		controller = new MainController();
	}

	@Override
	public StackPane getPane() {
		return controller.getPane();
	}

	@Override
	public void setInnerPane(Pane pane) {
		controller.setInnerPane(pane);
	}

	@Override
	public void removeInnerPane() {
		controller.removeInnerPane();
	}

}
