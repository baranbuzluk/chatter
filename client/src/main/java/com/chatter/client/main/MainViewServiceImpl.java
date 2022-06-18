package com.chatter.client.main;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.client.enums.ClientEvent;
import com.chatter.listener.api.EventInfo;
import com.chatter.listener.api.EventManager;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

@Component
public class MainViewServiceImpl implements MainViewService {

	private EventManager eventManager;

	private Stage mainStage;

	private Scene mainScene;

	@Autowired
	public MainViewServiceImpl(EventManager eventManager) {
		this.eventManager = eventManager;
	}

	@Override
	public void show(Pane pane) {
		Objects.requireNonNull(pane, "pane can not be null!");
		if (mainStage != null) {
			mainScene.setRoot(pane);
			mainStage.sizeToScene();
			mainStage.show();
			mainStage.centerOnScreen();
			mainStage.toFront();
		}
	}

	@Override
	public void setMainStage(Stage stage) {
		mainStage = Objects.requireNonNull(stage, "stage can not be null!");
		StackPane dummy = new StackPane();
		this.mainScene = new Scene(dummy);
		mainStage.setScene(mainScene);
		EventInfo startedApplicationEvent = new EventInfo(ClientEvent.STARTED_APPLICATION);
		eventManager.sendEvent(startedApplicationEvent);

	}

}
