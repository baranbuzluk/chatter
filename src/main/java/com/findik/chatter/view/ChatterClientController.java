package com.findik.chatter.view;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.findik.chatter.entity.Message;

import javafx.application.Platform;
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
	private ListView<Message> listViewMessage;

	@FXML
	private StackPane rootPane;

	@FXML
	private TextArea txtAreaMessage;

	private List<Consumer<Message>> messageAddListeners = new ArrayList<>();

	public ChatterClientController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatterClient.fxml"));
			loader.setController(this);
			loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void initialize() {
		initSendMessageButtonEventHandler();
	}

	public StackPane getRootPane() {
		return rootPane;
	}

	public void addMessageAddListener(Consumer<Message> value) {
		if (!messageAddListeners.contains(value)) {
			messageAddListeners.add(value);
		}
	}

	private void initSendMessageButtonEventHandler() {
		btnSendMessage.setOnMouseClicked(e -> {
			Message message = getMessageFromTxtArea();
			if (message != null) {
				listViewMessage.getItems().add(message);
				listViewMessage.scrollTo(message);
				messageAddListeners.parallelStream().forEach(listener -> listener.accept(message));
			}
		});

	}

	private Message getMessageFromTxtArea() {
		String text = txtAreaMessage.getText().trim();
		txtAreaMessage.setText("");
		if (text == null || text.isBlank()) {
			return null;
		}
		Message message = new Message();
		message.setContent(text);
		message.setUsername("Username");
		return message;
	}

	public void setMessages(List<Message> messages) {
		if (messages == null || messages.isEmpty()) {
			return;
		}
		Platform.runLater(() -> {
			listViewMessage.getItems().clear();
			listViewMessage.getItems().addAll(messages);
			int lastItemIndex = listViewMessage.getItems().size() - 1;
			listViewMessage.scrollTo(lastItemIndex);
		});
	}

}
