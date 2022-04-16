package com.findik.chatter.window.client.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.abstracts.AbstractGuiWindow;
import com.findik.chatter.abstracts.IMainWindowService;
import com.findik.chatter.abstracts.IMessageXMLFileService;
import com.findik.chatter.entity.Message;
import com.findik.chatter.enums.ChatterEvent;
import com.findik.chatter.enums.ChatterEventProperties;
import com.findik.chatter.listener.api.EventListener;
import com.findik.chatter.listener.api.EventManager;
import com.findik.chatter.listener.impl.EventInfo;
import com.findik.chatter.repository.IMessageRepository;
import com.findik.chatter.window.client.view.ChatterClientController;

import javafx.application.Platform;

@Component
public class ChatterClientWindow extends AbstractGuiWindow<ChatterClientController> implements EventListener {

	@Autowired
	private IMessageRepository messageRepository;

	@Autowired
	private IMainWindowService mainWindowService;

	@Autowired
	private IMessageXMLFileService messageXMLFileService;

	@Autowired
	private EventManager eventManager;

	@Override
	protected ChatterClientController createController() {
		ChatterClientController chatterClientController = new ChatterClientController();
		chatterClientController.setMessageAddedEventHandler(e -> {
			EventInfo eventInfo = new EventInfo(ChatterEvent.ADDED_MESSAGE);
			eventInfo.put(ChatterEventProperties.MESSAGE, e);
			eventManager.sendEvent(eventInfo);
		});
		return chatterClientController;
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.getEvent() == ChatterEvent.ADDED_MESSAGE) {
			Message object = (Message) eventInfo.get(ChatterEventProperties.MESSAGE);
			messageRepository.save(object);
			messageXMLFileService.writeToXml(object);
		} else if (eventInfo.getEvent() == ChatterEvent.LOGGED_IN_ACCOUNT) {
			Platform.runLater(() -> mainWindowService.show(getRootPane()));
			List<Message> messagesByCreatedAtAscending = messageRepository.findAllByOrderByCreatedAtAsc();
			controller.setMessages(messagesByCreatedAtAscending);
		}
	}

}
