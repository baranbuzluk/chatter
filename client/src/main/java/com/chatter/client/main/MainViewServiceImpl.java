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

	@Autowired
	public MainViewServiceImpl(EventManager eventManager) {
		this.eventManager = eventManager;
	}

	@Override
	public void show(Pane pane) {
		if (this.mainStage == null)
			return;

		Objects.requireNonNull(pane, "pane can not be null!");
		Scene scene = this.mainStage.getScene();
		scene.setRoot(pane);
		this.mainStage.sizeToScene();
		this.mainStage.show();
		this.mainStage.centerOnScreen();
		this.mainStage.toFront();

	}

	@Override
	public void setMainStage(Stage stage) {
		this.mainStage = Objects.requireNonNull(stage, "stage must not be null!");
		StackPane dummy = new StackPane();
		Scene scene = new Scene(dummy);
		this.mainStage.setScene(scene);

		EventInfo startedApplicationEvent = new EventInfo(ClientEvent.STARTED_APPLICATION);
		this.eventManager.sendEvent(startedApplicationEvent);
	}

}
