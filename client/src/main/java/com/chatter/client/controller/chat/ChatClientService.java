package com.chatter.client.controller.chat;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.client.main.MainViewService;
import com.chatter.client.xml.MessageXMLFileService;
import com.chatter.core.ChatterService;
import com.chatter.core.ControllerNotInitializedException;
import com.chatter.data.entity.Message;
import com.chatter.data.repository.MessageRepository;
import com.chatter.listener.api.EventInfo;
import com.chatter.listener.api.EventManager;

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
