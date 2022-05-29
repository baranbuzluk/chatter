package com.chatter.client.controller.chat;

import java.util.List;

import com.chatter.client.enums.ClientEvent;
import com.chatter.client.enums.ClientEventProperties;
import com.chatter.core.AbstractController;
import com.chatter.data.entity.Message;
import com.chatter.listener.api.EventInfo;
import com.chatter.listener.api.EventListener;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class ChatClientController extends AbstractController<ChatClientService> implements EventListener {

	@FXML
	private Button btnSendMessage;

	@FXML
	private ListView<Message> listViewMessage;

	@FXML
	private TextField txtMessage;

	public ChatClientController(ChatClientService service) {
		super("ChatClient.fxml", service);
	}

	@FXML
	private void initialize() {
		btnSendMessage.setOnMouseClicked(e -> sendMessageOperations());

		txtMessage.setOnKeyPressed(key -> {
			if (key.getCode() == KeyCode.ENTER) {
				sendMessageOperations();
			}
		});
	}

	private void sendMessageOperations() {
		Message message = getMessageFromTxtArea();
		if (message != null) {
			listViewMessage.getItems().add(message);
			listViewMessage.scrollTo(message);

			EventInfo eventInfo = new EventInfo(ClientEvent.ADDED_MESSAGE);
			eventInfo.put(ClientEventProperties.MESSAGE, message);
			service.sendEvent(eventInfo);
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
		if (messages == null || messages.isEmpty() || listViewMessage == null) {
			return;
		}
		Platform.runLater(() -> {
			listViewMessage.getItems().clear();
			listViewMessage.getItems().addAll(messages);
			int lastItemIndex = listViewMessage.getItems().size() - 1;
			listViewMessage.scrollTo(lastItemIndex);
		});
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ClientEvent.ADDED_MESSAGE) {
			Message message = (Message) eventInfo.get(ClientEventProperties.MESSAGE);
			service.sendMessage(message);
		} else if (eventInfo.getEvent() == ClientEvent.LOGGED_IN_ACCOUNT) {
			Platform.runLater(() -> service.showMainWindow(getPane()));
			List<Message> messagesByCreatedAtAscending = service.findAllByOrderByCreatedAtAsc();
			setMessages(messagesByCreatedAtAscending);
		}
	}

}
