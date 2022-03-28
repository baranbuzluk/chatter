package com.findik.chatter.window.client.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.abstracts.AbstractGuiWindow;
import com.findik.chatter.abstracts.IMainWindowService;
import com.findik.chatter.abstracts.IMessageXMLFileService;
import com.findik.chatter.entity.Account;
import com.findik.chatter.entity.Message;
import com.findik.chatter.listener.api.IMessageAddedEventListener;
import com.findik.chatter.listener.api.IUserLoggedInEventListener;
import com.findik.chatter.listener.manager.MessageActionEventManager;
import com.findik.chatter.repository.IMessageRepository;
import com.findik.chatter.window.client.view.ChatterClientController;

@Component
public class ChatterClientWindow extends AbstractGuiWindow<ChatterClientController>
		implements IMessageAddedEventListener, IUserLoggedInEventListener {

	@Autowired
	private IMessageRepository messageRepository;

	@Autowired
	private IMainWindowService mainWindowService;

	@Autowired
	private IMessageXMLFileService messageXMLFileService;

	@Autowired
	private MessageActionEventManager messageActionEventManager;

	@Override
	protected ChatterClientController createController() {
		ChatterClientController chatterClientController = new ChatterClientController();
		chatterClientController
				.setMessageAddedEventHandler(e -> messageActionEventManager.notifyMessageAddedEventListeners(e));
		return chatterClientController;
	}

	@Override
	public void updateMessageAddedEvent(Message addedMessage) {
		messageRepository.save(addedMessage);
		messageXMLFileService.writeToXml(addedMessage);
	}

	@Override
	public void updateUserLoggedInEvent(Account account) {
		mainWindowService.show(getRootPane());
		List<Message> messagesByCreatedAtAscending = messageRepository.findAllByOrderByCreatedAtAsc();
		controller.setMessages(messagesByCreatedAtAscending);
	}

}
