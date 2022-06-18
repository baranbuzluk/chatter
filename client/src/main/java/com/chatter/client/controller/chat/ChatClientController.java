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
	private ListView<Message> messageListView;

	@FXML
	private TextField messageTextField;

	@FXML
	private Button sendMessageButton;

	public ChatClientController(ChatClientService service) {
		super("ChatClient.fxml", service);
	}

	@FXML
	private void initialize() {
		sendMessageButton.setOnMouseClicked(e -> sendMessageOperations());

		messageTextField.setOnKeyPressed(key -> {
			if (key.getCode() == KeyCode.ENTER) {
				sendMessageOperations();
			}
		});
	}

	private void sendMessageOperations() {
		Message message = getMessageFromTxtArea();
		if (message != null) {
			messageListView.getItems().add(message);
			messageListView.scrollTo(message);

			EventInfo eventInfo = new EventInfo(ClientEvent.ADDED_MESSAGE);
			eventInfo.put(ClientEventProperties.MESSAGE, message);
			service.sendEvent(eventInfo);
		}
	}

	private Message getMessageFromTxtArea() {
		String text = messageTextField.getText().trim();
		messageTextField.setText("");
		if (text == null || text.isBlank()) {
			return null;
		}
		Message message = new Message();
		message.setContent(text);
		message.setUsername("Username");
		return message;
	}

	public void setMessages(List<Message> messages) {
		if (messages == null || messages.isEmpty() || messageListView == null) {
			return;
		}
		Platform.runLater(() -> {
			messageListView.getItems().clear();
			messageListView.getItems().addAll(messages);
			int lastItemIndex = messageListView.getItems().size() - 1;
			messageListView.scrollTo(lastItemIndex);
		});
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ClientEvent.ADDED_MESSAGE) {
			Message message = (Message) eventInfo.get(ClientEventProperties.MESSAGE);
			service.sendMessage(message);
		} else if (eventInfo.getEvent() == ClientEvent.LOGGED_IN_ACCOUNT) {
			// FIXME: burakcantemur tarafından düzeltilecektir.
//			Platform.runLater(() -> service.showMainWindow(getPane()));
//			List<Message> messagesByCreatedAtAscending = service.findAllByOrderByCreatedAtAsc();
//			setMessages(messagesByCreatedAtAscending);
		}
	}

}
