package com.findik.chatter.window.client.view;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.findik.chatter.abstracts.AbstractJFXController;
import com.findik.chatter.entity.Message;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class ChatterClientController extends AbstractJFXController {

	@FXML
	private Button btnSendMessage;

	@FXML
	private ListView<Message> listViewMessage;

	@FXML
	private TextField txtMessage;

	private List<Consumer<Message>> messageAddListeners = new ArrayList<>();

	public ChatterClientController() {
		super("ChatterClient.fxml");
	}

	@Override
	protected void initController() {
		initSendMessageButtonEventHandler();
		initMessageTextFieldEnterButtonEventHandler();
	}

	private void initMessageTextFieldEnterButtonEventHandler() {
		txtMessage.setOnKeyPressed(key -> {
			if (key.getCode() == KeyCode.ENTER) {
				sendMessageOperations();
			}
		});

	}

	public void addMessageAddListener(Consumer<Message> value) {
		if (!messageAddListeners.contains(value)) {
			messageAddListeners.add(value);
		}
	}

	private void initSendMessageButtonEventHandler() {
		btnSendMessage.setOnMouseClicked(e -> sendMessageOperations());
	}

	private void sendMessageOperations() {
		Message message = getMessageFromTxtArea();
		if (message != null) {
			listViewMessage.getItems().add(message);
			listViewMessage.scrollTo(message);
			messageAddListeners.parallelStream().forEach(listener -> listener.accept(message));
		}
	}

	private Message getMessageFromTxtArea() {
		String text = txtMessage.getText().trim();
		txtMessage.setText("");
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
