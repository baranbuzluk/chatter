package com.chatter.view;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

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
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
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
	private Button btnOpenCamera;

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
		BooleanBinding isSelected = listViewFriends.getSelectionModel().selectedItemProperty().isNull();
		btnOpenCamera.disableProperty().bind(isSelected);

	}

	@FXML
	void btnOpenCameraOnAction(ActionEvent event) {
		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());

		WebcamPanel panel = new WebcamPanel(webcam);
		panel.setFPSDisplayed(true);
		panel.setDisplayDebugInfo(true);
		panel.setImageSizeDisplayed(true);
		panel.setMirrored(true);

		JFrame window = new JFrame("Test Webcam Panel");
		JButton captureButton = new JButton("Capture");

		captureButton.addActionListener((actionEvent) -> {
			BufferedImage bim = webcam.getImage();
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			try {
				ImageIO.write(bim, "PNG", os);
				InputStream is = new ByteArrayInputStream(os.toByteArray());
				Message message = getMessage();
				message.setContent(is.toString());
				sendMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		window.setLayout(new BorderLayout());
		window.add(panel, BorderLayout.CENTER);
		window.add(captureButton, BorderLayout.SOUTH);
		window.setResizable(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
	}

	private void sendMessage(Message message) {
		if (messageService.sendMessage(message)) {
			listViewMessages.getItems().add(message);
			listViewMessages.scrollTo(message);
		}
	}

	private Message getMessage() {
		Message message = new Message();
		String selectedFriend = listViewFriends.getSelectionModel().getSelectedItem();
		message.setCreatedTime(LocalDateTime.now());
		try {
			message.setSenderHostAddress(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		message.setContent(textFieldMessage.getText());
		message.setRecipientHostAddress(selectedFriend);
		return message;
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
		} else if (eventInfo.event == ChatterEvent.INCOMING_IMAGE_MESSAGE) {
			// TODO IMAGE CONTAINER design
		}
	}

}
