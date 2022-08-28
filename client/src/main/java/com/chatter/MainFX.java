package com.chatter;

import com.chatter.client.main.MainViewService;
import com.chatter.core.configuration.ChatterApplicationContext;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainFX extends Application {

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
