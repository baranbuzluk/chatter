package com.findik.chatter.window.client.impl;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.abstracts.window.IWindow;
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

	private ChatterClientController controller;

	@PostConstruct
	private void postConstruct() {
		initController();
	}

	private void initController() {
		controller = new ChatterClientController();
		controller.addMessageAddListener(e -> Optional.ofNullable(e).ifPresent(messageRepository::save));
		controller.setMessages(messageRepository.findAllByOrderByCreatedAtAsc());
		mainWindowService.setInnerPane(controller.getPane());
	}

	@Override
	public StackPane getPane() {
		return controller.getPane();
	}
}
