package com.chatter.view.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.dto.MessageDto;
import com.chatter.event.ChatterEvent;
import com.chatter.event.ChatterEventListener;
import com.chatter.event.EventInfo;
import com.chatter.event.EventService;
import com.chatter.event.Variable;
import com.chatter.service.MessageService;
import com.chatter.view.ViewService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

@Component
public class ChatClientController extends StackPane implements ChatterEventListener {

	@FXML
	private Button buttonSendMessage;

	@FXML
	private ListView<MessageDto> listViewMessages;

	@FXML
	private TextField textFieldMessage;

	@FXML
	private MenuItem menuItemLogOut;

	private MessageService messageService;

	private ViewService viewService;

	private EventService eventService;

	@Autowired
	public ChatClientController(MessageService messageService, ViewService viewService, EventService eventService) {
		this.messageService = messageService;
		this.viewService = viewService;
		this.eventService = eventService;
		JavaFXUtils.loadFXMLWithRoot(this, "Chat.fxml");
	}

	@FXML
	void initialize() {
		loadAllMessages();
	}

	@FXML
	void menuItemLogOutOnAction(ActionEvent event) {
		eventService.sendEvent(new EventInfo(ChatterEvent.OPEN_LOGIN_VIEW));
	}

	@FXML
	void buttonSendMessageOnAction(ActionEvent event) {
		String content = textFieldMessage.getText();
		if (content.isBlank()) {
			return;
		}
		textFieldMessage.setText("");

		MessageDto sendMessage = messageService.sendMessage(content, null);
		addMessageToListView(sendMessage);
	}

	@FXML
	void rootPaneOnKeyPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			buttonSendMessageOnAction(null);
		}
	}

	private void addMessageToListView(MessageDto message) {
		listViewMessages.getItems().add(message);
		listViewMessages.scrollTo(message);
	}

	private void loadAllMessages() {
		List<MessageDto> messageDtos = messageService.getAllMessages();
		listViewMessages.getItems().clear();
		listViewMessages.getItems().addAll(messageDtos);
		int lastItemIndex = listViewMessages.getItems().size() - 1;
		listViewMessages.scrollTo(lastItemIndex);
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.event == ChatterEvent.OPEN_CHAT_VIEW) {
			viewService.show(this);
		} else if (eventInfo.event == ChatterEvent.INCOMING_MESSAGE) {
			MessageDto messageDto = (MessageDto) eventInfo.getVariable(Variable.MESSAGE);
			listViewMessages.getItems().add(messageDto);
		}
	}

}
