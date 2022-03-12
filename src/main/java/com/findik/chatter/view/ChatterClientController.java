package com.findik.chatter.view;

import com.findik.chatter.config.ChatterApplicationContext;
import com.findik.chatter.entity.Message;
import com.findik.chatter.repository.IMessageRepository;

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

	private IMessageRepository messageRepository;

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
		messageRepository = ChatterApplicationContext.getBean(IMessageRepository.class);
		btnSendMessage.setOnMouseClicked(e -> {
			messageRepository.save(new Message("baran", txtAreaMessage.getText()));
		});
	}

	public StackPane getRootPane() {
		return rootPane;
	}

}
