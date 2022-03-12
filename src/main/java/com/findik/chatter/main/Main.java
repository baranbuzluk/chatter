package com.findik.chatter.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.findik.chatter.config.ChatterApplicationContext;
import com.findik.chatter.view.ChatterClientController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

	private AnnotationConfigApplicationContext applicationContext;

	@Override
	public void start(Stage primaryStage) {
		applicationContext = new AnnotationConfigApplicationContext(ChatterApplicationContext.class);
		ChatterClientController chatterClientController = new ChatterClientController();
		StackPane rootPane = chatterClientController.getRootPane();
		Scene scene = new Scene(rootPane);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@Override
	public void stop() throws Exception {
		super.stop();
		applicationContext.close();
	}

	public static void main(String[] args) {
		launch(args);

	}
}
