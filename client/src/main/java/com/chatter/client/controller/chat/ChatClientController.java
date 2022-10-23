package com.chatter.client.controller.chat;

import java.util.List;

import com.chatter.client.enums.ClientEvent;
import com.chatter.client.enums.ClientEventProperties;
import com.chatter.client.session.ChatterSession;
import com.chatter.core.abstracts.AbstractController;
import com.chatter.core.entity.Account;
import com.chatter.core.entity.Message;
import com.chatter.event.api.ChatterEventListener;
import com.chatter.event.api.EventInfo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ChatClientController extends AbstractController<ChatClientService> implements ChatterEventListener {

	@FXML
	private Button buttonSendMessage;

	@FXML
	private ListView<Message> listViewMessages;

	@FXML
	private TextField textFieldMessage;
	@FXML
	private MenuItem menuItemLogOut;

	public ChatClientController(ChatClientService service) {
		super("ChatClient.fxml", service);
	}

	@FXML
	void buttonSendMessageOnAction(ActionEvent event) {
		runOutgoingMessageOperations();
	}

	@FXML
	void rootPaneOnKeyPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			buttonSendMessageOnAction(null);
		}
	}

	private void runOutgoingMessageOperations() {
		String text = textFieldMessage.getText();
		if (text.isBlank()) {
			return;
		}
		textFieldMessage.setText("");
		Account activeAccount = ChatterSession.getInstance().getActiveAccount();
		Message message = new Message(activeAccount, text);
		activeAccount.addMessage(message);
		addMessageToListView(message);
		service.saveOutgoingMessage(message);
	}

	private void addMessageToListView(Message message) {
		listViewMessages.getItems().add(message);
		listViewMessages.scrollTo(message);
	}

	private void loadAllMessageFromDatabase() {
		List<Message> allMessage = service.findAllByOrderByCreatedAtAsc();
		listViewMessages.getItems().clear();
		listViewMessages.getItems().addAll(allMessage);
		int lastItemIndex = listViewMessages.getItems().size() - 1;
		listViewMessages.scrollTo(lastItemIndex);
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		Platform.runLater(() -> {
			if (eventInfo.getEvent() == ClientEvent.LOGGED_IN_ACCOUNT) {
				loadAllMessageFromDatabase();
				service.showMainWindow(getPane());
			} else if (eventInfo.getEvent() == ClientEvent.INCOMING_MESSAGE) {
				Message message = (Message) eventInfo.get(ClientEventProperties.MESSAGE);
				addMessageToListView(message);
			}
		});
	}

	@FXML
	void menuItemLogOutOnAction(ActionEvent event) {
		service.sendEvent(new EventInfo(ClientEvent.STARTED_APPLICATION));
	}
}
