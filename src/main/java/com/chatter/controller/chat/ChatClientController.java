package com.chatter.controller.chat;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.connection.CommunicationManager;
import com.chatter.data.entity.Message;
import com.chatter.data.util.FileOutputType;
import com.chatter.data.util.MessageUtils;
import com.chatter.event.ChatterEvent;
import com.chatter.event.ChatterEventProperties;
import com.chatter.event.EventInfo;
import com.chatter.service.AbstractController;
import com.chatter.service.CommonService;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

@Component
public class ChatClientController extends AbstractController {

	@FXML
	private Button buttonSendMessage;

	@FXML
	private ListView<Message> listViewMessages;

	@FXML
	private TextField textFieldMessage;

	@FXML
	private MenuItem menuItemLogOut;

	@FXML
	private ListView<String> listViewHostAddress;

	private final CommunicationManager communicationManager;

	@Autowired
	public ChatClientController(CommonService commonService, CommunicationManager communicationManager) {
		super("Chat.fxml", commonService);
		this.communicationManager = communicationManager;

	}

	@FXML
	void initialize() {
		listViewHostAddress.getSelectionModel().selectedItemProperty().addListener((change, oldValue, newValue) -> {
			if (newValue != null) {

			}
		});
	}

	@FXML
	void buttonSendMessageOnAction(ActionEvent event) {
		String content = textFieldMessage.getText();
		if (content.isBlank()) {
			return;
		}

		textFieldMessage.setText("");

		String recipientHostAddress = listViewHostAddress.getSelectionModel().getSelectedItem();
		String senderHostAdress = communicationManager.getHostAddress();
		Message message = new Message(content, recipientHostAddress, senderHostAdress);

		MessageUtils.write(message, new File("C:\\CHATTER"), FileOutputType.XML);
		commonService.sendMessage(message);

		addMessageToListView(message);

	}

	@FXML
	void rootPaneOnKeyPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			buttonSendMessageOnAction(null);
		}
	}

	private void addMessageToListView(Message message) {
		listViewMessages.getItems().add(message);
		listViewMessages.scrollTo(message);
	}

	private void loadAllMessageFromDatabase() {
		List<Message> allMessage = commonService.getAllMessagesFromDatabase();
		listViewMessages.getItems().clear();
		listViewMessages.getItems().addAll(allMessage);
		int lastItemIndex = listViewMessages.getItems().size() - 1;
		listViewMessages.scrollTo(lastItemIndex);
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		ChatterEvent event = eventInfo.getEvent();
		Platform.runLater(() -> {
			if (event == ChatterEvent.LOGGED_IN_ACCOUNT) {
				commonService.showInMainWindow(getRootPane());
				loadAllMessageFromDatabase();
				listViewHostAddress.getItems().addAll(communicationManager.getActiveHostAddressList());
			} else if (event == ChatterEvent.INCOMING_MESSAGE) {
				Message message = (Message) eventInfo.get(ChatterEventProperties.MESSAGE);
				addMessageToListView(message);
			}
		});
	}

	@FXML
	void menuItemLogOutOnAction(ActionEvent event) {
		commonService.sendEvent(new EventInfo(ChatterEvent.STARTED_APPLICATION));
	}
}
