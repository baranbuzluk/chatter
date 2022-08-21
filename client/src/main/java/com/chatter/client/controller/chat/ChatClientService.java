package com.chatter.client.controller.chat;

import java.io.File;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.abstracts.ChatterService;
import com.chatter.client.main.MainViewService;
import com.chatter.entity.Message;
import com.chatter.event.listener.EventInfo;
import com.chatter.event.listener.EventManager;
import com.chatter.repository.MessageRepository;
import com.chatter.util.XmlUtils;

import javafx.scene.layout.Pane;

@Component
public class ChatClientService implements ChatterService {

	private static final String UNDERSCORE = "_";

	private MessageRepository messageRepository;

	private MainViewService mainWindowService;

	private EventManager eventManager;

	private ChatClientController controller;

	@Autowired
	public ChatClientService(MessageRepository messageRepository, MainViewService mainWindowService,
			EventManager eventManager) {
		this.messageRepository = messageRepository;
		this.mainWindowService = mainWindowService;
		this.eventManager = eventManager;
		controller = new ChatClientController(this);
		eventManager.registerListener(controller);
	}

	public void sendEvent(EventInfo eventInfo) {
		eventManager.sendEvent(Objects.requireNonNull(eventInfo, "Can not be null EventInfo!"));
	}

	public Message saveToDatabase(Message message) {
		return messageRepository.saveAndFlush(message);
	}

	public File writeToXmlFile(Message message) {
		Message msg = Objects.requireNonNull(message, "Message can not  be null!");
		String fileName = generateFileName(msg);
		File xmlFile = Paths.get("C:", "CHATTER", fileName).toFile();
		XmlUtils.writeObjectToFile(msg, xmlFile);
		return xmlFile;
	}

	public void showMainWindow(Pane pane) {
		mainWindowService.show(Objects.requireNonNull(pane, "Pane can not  be null!"));
	}

	public List<Message> findAllByOrderByCreatedAtAsc() {
		return messageRepository.findAllByOrderByCreatedAtAsc();
	}

	private String generateFileName(Message message) {
		Message msg = Objects.requireNonNull(message, "message can not  be null!");
		StringBuilder fileNameBuilder = new StringBuilder();
		fileNameBuilder.append("Message");
		fileNameBuilder.append(UNDERSCORE);
		fileNameBuilder.append(msg.getId());
		fileNameBuilder.append(UNDERSCORE);
		fileNameBuilder.append(msg.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")));
		fileNameBuilder.append(".xml");
		return fileNameBuilder.toString();
	}

}
