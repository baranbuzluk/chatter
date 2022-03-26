package com.findik.chatter.main;

import com.findik.chatter.abstracts.IMainWindowService;
import com.findik.chatter.config.ChatterApplicationContext;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {

	private static IMainWindowService mainWindowService;

	@Override
	public void start(Stage primaryStage) {
		mainWindowService.setMainStage(primaryStage);
	}

	public static void main(String[] args) {
		ChatterApplicationContext.start();
		mainWindowService = ChatterApplicationContext.getBean(IMainWindowService.class);
		launch(args);
	}
}
