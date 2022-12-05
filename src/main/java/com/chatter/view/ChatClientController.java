package com.chatter.view;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.data.Message;
import com.chatter.event.ChatterEvent;
import com.chatter.event.ChatterEventListener;
import com.chatter.event.EventInfo;
import com.chatter.event.EventService;
import com.chatter.event.Variable;
import com.chatter.service.CommunicationService;
import com.chatter.service.MessageService;

import javafx.application.Platform;
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
class ChatClientController extends StackPane implements ChatterEventListener {

	@FXML
	private Button buttonSendMessage;

	@FXML
	private ListView<Message> listViewMessages;

	@FXML
	private TextField textFieldMessage;

	@FXML
	private ListView<String> listViewFriends;

	@FXML
	private MenuItem menuItemLogOut;

	private MessageService messageService;

	private ViewService viewService;

	private EventService eventService;

	private CommunicationService communicationService;

	@Autowired
	public ChatClientController(MessageService messageService, ViewService viewService, EventService eventService,
			CommunicationService communicationService) {
		this.messageService = messageService;
		this.viewService = viewService;
		this.eventService = eventService;
		this.communicationService = communicationService;
		JavaFXUtils.loadFXMLWithRoot(this, "Chat.fxml");
	}

	@FXML
	void initialize() {
		buttonRefreshOnlineHostAddressesOnAction(null);
	}

	@FXML
	void buttonRefreshOnlineHostAddressesOnAction(ActionEvent event) {
		new Thread(() -> {
			List<String> hostAddresses = communicationService.getOnlineHostAddresses();
			Platform.runLater(() -> {
				listViewFriends.getItems().clear();
				listViewFriends.getItems().addAll(hostAddresses);
				listViewFriends.refresh();
			});
		}).start();
	}

	@FXML
	void menuItemLogOutOnAction(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	void buttonSendMessageOnAction(ActionEvent event) {
		String content = textFieldMessage.getText();
		String selectedIpAddress = listViewFriends.getSelectionModel().getSelectedItem();
		if (content.isBlank() || selectedIpAddress == null) {
			return;
		}
		textFieldMessage.setText("");

		Message message = new Message();
		message.setContent(content);
		message.setCreatedTime(LocalDateTime.now());
		message.setRecipientHostAddress(selectedIpAddress);
		try {
			message.setSenderHostAddress(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		if (messageService.sendMessage(message)) {
			listViewMessages.getItems().add(message);
			listViewMessages.scrollTo(message);
		}
	}

	@FXML
	void rootPaneOnKeyPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			buttonSendMessageOnAction(null);
		}
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.event == ChatterEvent.OPEN_CHAT_VIEW) {
			viewService.show(this);
		} else if (eventInfo.event == ChatterEvent.INCOMING_MESSAGE) {
			Message messageDto = (Message) eventInfo.getVariable(Variable.MESSAGE);
			listViewMessages.getItems().add(messageDto);
		}
	}

}
