package com.chatter.main;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.enums.ChatterEvent;
import com.chatter.listener.EventInfo;
import com.chatter.listener.EventManager;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@Component
public class MainViewServiceImpl implements MainViewService {

	@Autowired
	private EventManager eventManager;

	private Optional<Stage> mainStageOptional = Optional.empty();

	private Scene mainScene;

	@Override
	public void show(Pane pane) {
		Objects.requireNonNull(pane);
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
			eventManager.sendEvent(new EventInfo(ChatterEvent.STARTED_APPLICATION));
		});
	}

}
