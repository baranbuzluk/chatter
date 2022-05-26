package com.findik.chatter.controller.chat;

import java.io.IOException;
import java.util.List;

import com.findik.chatter.core.AbstractController;
import com.findik.chatter.entity.Message;
import com.findik.chatter.enums.ChatterEvent;
import com.findik.chatter.enums.ChatterEventProperties;
import com.findik.chatter.listener.ChatterEventListener;
import com.findik.chatter.listener.EventInfo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class ChatClientController extends AbstractController<ChatClientService> implements ChatterEventListener {

	@FXML
	private Button btnSendMessage;

	@FXML
	private ListView<Message> listViewMessage;

	@FXML
	private TextField txtMessage;

	public ChatClientController(ChatClientService service) throws IOException {
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

			EventInfo eventInfo = new EventInfo(ChatterEvent.ADDED_MESSAGE);
			eventInfo.put(ChatterEventProperties.MESSAGE, message);
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

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ChatterEvent.ADDED_MESSAGE) {
			Message object = (Message) eventInfo.get(ChatterEventProperties.MESSAGE);
			service.saveToDatabase(object);
			service.writeToXml(object);
		} else if (eventInfo.getEvent() == ChatterEvent.LOGGED_IN_ACCOUNT) {
			Platform.runLater(() -> service.showMainWindow(getPane()));
			List<Message> messagesByCreatedAtAscending = service.findAllByOrderByCreatedAtAsc();
			setMessages(messagesByCreatedAtAscending);
		}
	}

}
