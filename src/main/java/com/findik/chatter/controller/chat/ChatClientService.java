package com.findik.chatter.controller.chat;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.core.ChatterService;
import com.findik.chatter.core.ControllerNotInitializedException;
import com.findik.chatter.entity.Message;
import com.findik.chatter.listener.EventInfo;
import com.findik.chatter.listener.EventManager;
import com.findik.chatter.main.MainViewService;
import com.findik.chatter.repository.MessageRepository;
import com.findik.chatter.xml.MessageXMLFileService;

import javafx.scene.layout.Pane;

@Component
public class ChatClientService implements ChatterService {

	private MessageRepository messageRepository;

	private MainViewService mainWindowService;

	private MessageXMLFileService messageXMLFileService;

	private EventManager eventManager;

	private ChatClientController controller;

	@Autowired
	public ChatClientService(MessageRepository messageRepository, MainViewService mainWindowService,
			MessageXMLFileService messageXMLFileService, EventManager eventManager) {
		this.messageRepository = messageRepository;
		this.mainWindowService = mainWindowService;
		this.messageXMLFileService = messageXMLFileService;
		this.eventManager = eventManager;

		try {
			controller = new ChatClientController(this);
			eventManager.registerListener(controller);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ControllerNotInitializedException();
		}
	}

	public void sendEvent(EventInfo eventInfo) {
		eventManager.sendEvent(Objects.requireNonNull(eventInfo, "Can not be null EventInfo!"));
	}

	public void saveToDatabase(Message object) {
		messageRepository.saveAndFlush(Objects.requireNonNull(object, "Message can not  be null!"));
	}

	public void writeToXml(Message object) {
		messageXMLFileService.writeToXml(Objects.requireNonNull(object, "Message can not  be null!"));
	}

	public void showMainWindow(Pane pane) {
		mainWindowService.show(Objects.requireNonNull(pane, "Pane can not  be null!"));
	}

	public List<Message> findAllByOrderByCreatedAtAsc() {
		return messageRepository.findAllByOrderByCreatedAtAsc();
	}

}
