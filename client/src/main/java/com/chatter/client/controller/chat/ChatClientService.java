package com.chatter.client.controller.chat;

import java.io.File;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.client.main.MainViewService;
import com.chatter.core.abstracts.ChatterService;
import com.chatter.core.abstracts.FileMapper;
import com.chatter.core.entity.Message;
import com.chatter.core.event.listener.EventInfo;
import com.chatter.core.event.listener.EventManager;
import com.chatter.core.repository.MessageRepository;

import javafx.scene.layout.Pane;

@Component
public class ChatClientService implements ChatterService {

	private static final String UNDERSCORE = "_";

	private MessageRepository messageRepository;

	private MainViewService mainWindowService;

	private EventManager eventManager;

	private FileMapper<Message> messageFileMapper;

	private ChatClientController controller;

	@Autowired
	public ChatClientService(MessageRepository messageRepository, MainViewService mainWindowService,
			EventManager eventManager, FileMapper<Message> messageFileMapper) {
		this.messageRepository = messageRepository;
		this.mainWindowService = mainWindowService;
		this.eventManager = eventManager;
		this.messageFileMapper = messageFileMapper;
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
		messageFileMapper.writeToFile(msg, xmlFile);
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
