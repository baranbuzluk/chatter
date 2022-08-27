package com.chatter.client.controller.chat;

import java.util.List;
import java.util.Objects;

import com.chatter.client.enums.ClientEvent;
import com.chatter.client.enums.ClientEventProperties;
import com.chatter.core.abstracts.AbstractController;
import com.chatter.core.entity.Message;
import com.chatter.core.event.listener.ChatterEventListener;
import com.chatter.core.event.listener.EventInfo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class ChatClientController extends AbstractController<ChatClientService> implements ChatterEventListener {

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
			saveMessage(message);
			EventInfo eventInfo = new EventInfo(ClientEvent.OUTGOING_MESSAGE);
			eventInfo.put(ClientEventProperties.MESSAGE, message);
			service.sendEvent(eventInfo);
		}
		clearMessageTextField();
	}

	private void clearMessageTextField() {
		messageTextField.setText("");
	}

	private Message getMessageFromTxtArea() {
		String text = messageTextField.getText().trim();
		if (text.isEmpty()) {
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

		messageListView.getItems().clear();
		messageListView.getItems().addAll(messages);
		int lastItemIndex = messageListView.getItems().size() - 1;
		messageListView.scrollTo(lastItemIndex);
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ClientEvent.LOGGED_IN_ACCOUNT) {
			Platform.runLater(() -> {
				service.showMainWindow(getPane());
				setMessages(service.findAllByOrderByCreatedAtAsc());
			});
		} else if (eventInfo.getEvent() == ClientEvent.INCOMING_MESSAGE) {
			Message message = (Message) eventInfo.get(ClientEventProperties.MESSAGE);
			Platform.runLater(() -> saveMessage(message));
		}
	}

	public void saveMessage(Message message) {
		Objects.requireNonNull(message, "Message can not  be null!");

		messageListView.getItems().add(message);
		messageListView.scrollTo(message);
		service.saveToDatabase(message);
		service.writeToXmlFile(message);
	}

}
