package com.findik.chatter.window.client.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.abstracts.IMessageXMLFileService;
import com.findik.chatter.abstracts.IWindow;
import com.findik.chatter.entity.Message;
import com.findik.chatter.main.api.IMainWindowService;
import com.findik.chatter.repository.IMessageRepository;
import com.findik.chatter.window.client.api.IChatterClientWindow;
import com.findik.chatter.window.client.view.ChatterClientController;

import javafx.scene.layout.StackPane;

@Component
public class ChatterClientWindow implements IWindow, IChatterClientWindow {

	@Autowired
	private IMessageRepository messageRepository;

	@Autowired
	private IMainWindowService mainWindowService;

	@Autowired
	private IMessageXMLFileService messageXMLFileService;

	private ChatterClientController controller;

	@PostConstruct
	private void postConstruct() {
		initController();
	}

	private void initController() {
		controller = new ChatterClientController();
		controller.addMessageAddListener(e -> Optional.ofNullable(e).ifPresent(t -> {
			messageRepository.save(t);
			messageXMLFileService.writeToXml(t);
		}));
		List<Message> messagesByCreatedAtAscending = messageRepository.findAllByOrderByCreatedAtAsc();
		controller.setMessages(messagesByCreatedAtAscending);
		mainWindowService.setInnerPane(controller.getPane());
	}

	@Override
	public StackPane getPane() {
		return controller.getPane();
	}
}
