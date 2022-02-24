package com.findik.chatter.view;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

public class ChatterClientController {

	@FXML
	private Button btnSendMessage;

	@FXML
	private ListView<?> listViewMessage;

	@FXML
	private StackPane rootPane;

	@FXML
	private TextArea txtAreaMessage;

	public ChatterClientController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatterClient.fxml"));
			loader.setController(this);
			loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		init();
	}

	private void init() {

		btnSendMessage.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				btnSendMessage.setText("Test et");
			};
		});
	}

	public StackPane getRootPane() {
		return rootPane;
	}

}
