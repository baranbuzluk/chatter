package com.findik.chatter.main;

import com.findik.chatter.abstracts.IMainWindowService;
import com.findik.chatter.config.ChatterApplicationContext;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {

	@Override
	public void start(Stage primaryStage) {
		IMainWindowService mainWindowService = ChatterApplicationContext.getBean(IMainWindowService.class);
		mainWindowService.setMainStage(primaryStage);
	}

	public static void main(String[] args) {
		ChatterApplicationContext.start();
		launch(args);
		ChatterApplicationContext.close();
	}
}
