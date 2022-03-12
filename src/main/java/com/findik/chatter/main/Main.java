package com.findik.chatter.main;

import com.findik.chatter.config.ChatterApplicationContext;
import com.findik.chatter.window.ChatterClientWindow;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		ChatterApplicationContext.start();
		ChatterClientWindow chatterClientWindow = ChatterApplicationContext.getBean(ChatterClientWindow.class);
		StackPane rootPane = chatterClientWindow.getPane();
		Scene scene = new Scene(rootPane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
