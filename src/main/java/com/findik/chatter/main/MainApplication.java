package com.findik.chatter.main;

import com.findik.chatter.abstracts.window.IWindow;
import com.findik.chatter.config.ChatterApplicationContext;
import com.findik.chatter.enums.ApplicationConstant;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApplication extends Application {

	@Override
	public void start(Stage primaryStage) {
		ChatterApplicationContext.start();
		IWindow main = ChatterApplicationContext.getBean(ApplicationConstant.MAIN_WINDOW, IWindow.class);
		Pane rootPane = main.getPane();
		Scene scene = new Scene(rootPane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
