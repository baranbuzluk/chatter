package com.chatter.main;

import com.chatter.config.ChatterApplicationContext;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {

	@Override
	public void start(Stage primaryStage) {
		MainViewService mainWindowService = ChatterApplicationContext.getBean(MainViewService.class);
		mainWindowService.setMainStage(primaryStage);
	}

	public static void main(String[] args) {
		ChatterApplicationContext.start();
		launch(args);
		ChatterApplicationContext.close();
	}
}
