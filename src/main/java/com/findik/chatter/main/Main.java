package com.findik.chatter.main;

import com.findik.chatter.view.ChatterClientController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		ChatterClientController chatterClientController = new ChatterClientController();
		StackPane rootPane = chatterClientController.getRootPane();
		Scene scene = new Scene(rootPane);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);

	}
}
