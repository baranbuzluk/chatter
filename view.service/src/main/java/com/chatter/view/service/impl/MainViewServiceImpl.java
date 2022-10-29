package com.chatter.view.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.event.api.EventManager;
import com.chatter.event.data.ChatterEvent;
import com.chatter.event.data.EventInfo;
import com.chatter.view.service.MainViewService;

import javafx.scene.Parent;
import javafx.scene.Scene;
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
	public void show(Parent parent) {
		if (this.mainStage == null)
			return;

		Objects.requireNonNull(parent, "pane can not be null!");
		Scene scene = this.mainStage.getScene();
		scene.setRoot(parent);
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

		EventInfo startedApplicationEvent = new EventInfo(ChatterEvent.STARTED_APPLICATION);
		this.eventManager.sendEvent(startedApplicationEvent);
	}

}
