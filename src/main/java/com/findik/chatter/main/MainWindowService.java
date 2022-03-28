package com.findik.chatter.main;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.abstracts.IMainWindowService;
import com.findik.chatter.listener.manager.ApplicationEventManager;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@Component
public class MainWindowService implements IMainWindowService {

	@Autowired
	private ApplicationEventManager applicationEventManager;

	private Optional<Stage> mainStageOptional = Optional.empty();

	private Scene mainScene;

	@Override
	public void show(Pane pane) {
		mainStageOptional.ifPresent(mainStage -> {
			mainScene.setRoot(pane);
			mainStage.sizeToScene();
			mainStage.show();
			mainStage.centerOnScreen();
			mainStage.toFront();
		});
	}

	@Override
	public void setMainStage(Stage stage) {
		mainStageOptional = Optional.ofNullable(stage);

		mainStageOptional.ifPresent(mainStage -> {
			StackPane dummy = new StackPane();
			this.mainScene = new Scene(dummy);
			mainStage.setScene(mainScene);
			applicationEventManager.notifyStartedApplicationEventListeners();
		});
	}

}
