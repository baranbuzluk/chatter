package com.chatter.controller.chat;

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
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	private Button btnRefresh;

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
		BooleanBinding isSelected = listViewHostAddress.getSelectionModel().selectedItemProperty().isNull();
		textFieldMessage.disableProperty().bind(isSelected);
		buttonSendMessage.disableProperty().bind(isSelected);
		String hostAddress = this.communicationManager.getHostAddress();

		listViewHostAddress.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null) {
					List<Message> messages = commonService
							.findMessageByrecipientHostAddressAndSenderHostAdress(newValue, hostAddress);
					if (!messages.isEmpty()) {
						listViewMessages.getItems().clear();
						listViewMessages.getItems().addAll(messages);
					}

				}

			}

		});

	}

	@FXML
	void btnRefreshOnClick(ActionEvent event) {
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

		MessageUtils.write(message, FileOutputType.XML);
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
