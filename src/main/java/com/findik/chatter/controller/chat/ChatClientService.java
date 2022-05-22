package com.findik.chatter.controller.chat;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.core.ChatterService;
import com.findik.chatter.entity.Message;
import com.findik.chatter.enums.ChatterEvent;
import com.findik.chatter.enums.ChatterEventProperties;
import com.findik.chatter.listener.EventInfo;
import com.findik.chatter.listener.EventListener;
import com.findik.chatter.listener.EventManager;
import com.findik.chatter.main.MainViewService;
import com.findik.chatter.repository.MessageRepository;
import com.findik.chatter.xml.MessageXMLFileService;

import javafx.application.Platform;

@Component
public class ChatClientService implements ChatterService, EventListener {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private MainViewService mainWindowService;

	@Autowired
	private MessageXMLFileService messageXMLFileService;

	@Autowired
	private EventManager eventManager;

	private ChatClientController controller;

	public ChatClientService(MessageRepository messageRepository, MainViewService mainWindowService,
			MessageXMLFileService messageXMLFileService, EventManager eventManager) {
		super();
		this.messageRepository = messageRepository;
		this.mainWindowService = mainWindowService;
		this.messageXMLFileService = messageXMLFileService;
		this.eventManager = eventManager;
		try {
			controller = new ChatClientController(this);
		} catch (IOException e) {
			controller = null;
			e.printStackTrace();
		}
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ChatterEvent.ADDED_MESSAGE) {
			Message object = (Message) eventInfo.get(ChatterEventProperties.MESSAGE);
			messageRepository.save(object);
			messageXMLFileService.writeToXml(object);
		} else if (eventInfo.getEvent() == ChatterEvent.LOGGED_IN_ACCOUNT) {
			Platform.runLater(() -> mainWindowService.show(controller.getPane()));
			List<Message> messagesByCreatedAtAscending = messageRepository.findAllByOrderByCreatedAtAsc();
			controller.setMessages(messagesByCreatedAtAscending);
		}
	}

	public void sendEvent(EventInfo eventInfo) {
		eventManager.sendEvent(Objects.requireNonNull(eventInfo, "Can not be null EventInfo!"));
	}

}
