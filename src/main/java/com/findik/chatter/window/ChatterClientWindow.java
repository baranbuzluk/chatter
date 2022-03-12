package com.findik.chatter.window;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.repository.IMessageRepository;
import com.findik.chatter.view.ChatterClientController;

import javafx.scene.layout.StackPane;

@Component
public class ChatterClientWindow implements IWindow {

	@Autowired
	private IMessageRepository messageRepository;

	private ChatterClientController controller;

	@Override
	public StackPane getPane() {
		return controller.getRootPane();
	}

	@PostConstruct
	private void postConstruct() {
		initController();
	}

	private void initController() {
		controller = new ChatterClientController();
		controller.addMessageAddListener(e -> Optional.ofNullable(e).ifPresent(messageRepository::save));
	}

}
