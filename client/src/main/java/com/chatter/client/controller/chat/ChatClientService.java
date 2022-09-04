package com.chatter.client.controller.chat;

import java.io.File;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.client.main.MainViewService;
import com.chatter.core.abstracts.ChatterService;
import com.chatter.core.abstracts.MessageWriter;
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

	private MessageWriter<File> messageWriter;

	private ChatClientController controller;

	@Autowired
	public ChatClientService(MessageRepository messageRepository, MainViewService mainWindowService,
			EventManager eventManager, MessageWriter<File> messageWriter) {
		this.messageRepository = messageRepository;
		this.mainWindowService = mainWindowService;
		this.eventManager = eventManager;
		this.messageWriter = messageWriter;
		controller = new ChatClientController(this);
		eventManager.registerListener(controller);
	}

	public void sendEvent(EventInfo eventInfo) {
		eventManager.sendEvent(Objects.requireNonNull(eventInfo, "Can not be null EventInfo!"));
	}

	public void saveMessage(Message message) {
		messageRepository.saveAndFlush(message);
		messageWriter.write(message);
	}

	public void showMainWindow(Pane pane) {
		mainWindowService.show(Objects.requireNonNull(pane, "Pane can not  be null!"));
	}

	public List<Message> findAllByOrderByCreatedAtAsc() {
		return messageRepository.findAllByOrderByCreatedAtAsc();
	}

}
