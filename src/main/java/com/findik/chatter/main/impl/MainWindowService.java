package com.findik.chatter.main.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.abstracts.IApplicationEventListenerManager;
import com.findik.chatter.abstracts.IMainWindowService;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@Component
public class MainWindowService implements IMainWindowService {

	@Autowired
	private IApplicationEventListenerManager applicationEventListenerManager;

	private Optional<Stage> mainStageOptional = Optional.empty();

	private Scene mainScene;

	@Override
	public void setInnerPane(Pane pane) {
		mainStageOptional.ifPresent(mainStage -> {
			mainScene.setRoot(pane);
			mainStage.sizeToScene();
			mainStage.show();
			mainStage.centerOnScreen();
			mainStage.toFront();
		});
	}

	@Override
	public void removeInnerPane() {
		mainStageOptional.ifPresent(mainStage -> {

		});
	}

	@Override
	public void setMainStage(Stage mainStage) {
		this.mainStageOptional = Optional.ofNullable(mainStage);
		StackPane dummy = new StackPane();
		this.mainScene = new Scene(dummy);
		initStageAndScene();
	}

	private void initStageAndScene() {
		mainStageOptional.ifPresent(mainStage -> {
			mainStage.show();
			mainStage.setScene(mainScene);
			mainStage.centerOnScreen();
			mainStage.toBack();
			applicationEventListenerManager.notifyStartedApplicationEventListeners();
		});
	}

}
