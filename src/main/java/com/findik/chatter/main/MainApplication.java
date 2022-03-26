package com.findik.chatter.main;

import com.findik.chatter.abstracts.IWindow;
import com.findik.chatter.config.ChatterApplicationContext;
import com.findik.chatter.enums.ApplicationConstant;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApplication extends Application {

	private static IWindow mainWindow;

	@Override
	public void start(Stage primaryStage) {

		Pane rootPane = mainWindow.getPane();
		Scene scene = new Scene(rootPane);
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.sizeToScene();
		primaryStage.show();

	}

	public static void main(String[] args) {
		ChatterApplicationContext.start();
		mainWindow = ChatterApplicationContext.getBean(ApplicationConstant.MAIN_WINDOW, IWindow.class);
		launch(args);
	}
}
