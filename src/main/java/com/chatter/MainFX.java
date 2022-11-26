package com.chatter;

import com.chatter.configuration.ChatterApplicationContext;
import com.chatter.event.ChatterEvent;
import com.chatter.event.EventInfo;
import com.chatter.event.EventService;
import com.chatter.view.ViewService;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainFX extends Application {

	@Override
	public void start(Stage primaryStage) {
		ViewService mainWindowService = ChatterApplicationContext.getBean(ViewService.class);
		mainWindowService.setMainStage(primaryStage);

		EventService eventService = ChatterApplicationContext.getBean(EventService.class);
		eventService.sendEvent(new EventInfo(ChatterEvent.OPEN_LOGIN_VIEW));
	}

	public static void main(String[] args) {
		ChatterApplicationContext.start();
		launch(args);
	}

}
