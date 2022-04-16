package com.findik.chatter.window.client.view;

import java.util.List;
import java.util.Optional;
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

	private Optional<Consumer<Message>> messageAddedEventHandler = Optional.empty();

	public ChatterClientController() {
		super("ChatterClient.fxml");
	}

	@Override
	protected void afterControllerLoaded() {
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

	private void initSendMessageButtonEventHandler() {
		btnSendMessage.setOnMouseClicked(e -> sendMessageOperations());
	}

	private void sendMessageOperations() {
		Message message = getMessageFromTxtArea();
		if (message != null) {
			listViewMessage.getItems().add(message);
			listViewMessage.scrollTo(message);
			messageAddedEventHandler.ifPresent(e -> e.accept(message));
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

	public void setMessageAddedEventHandler(Consumer<Message> handler) {
		messageAddedEventHandler = Optional.ofNullable(handler);
	}

}
