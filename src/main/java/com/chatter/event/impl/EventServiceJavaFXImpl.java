package com.chatter.event.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.chatter.configuration.ChatterApplicationContext;
import com.chatter.event.ChatterEventListener;
import com.chatter.event.EventInfo;
import com.chatter.event.EventService;

import javafx.application.Platform;

@Component
class EventServiceJavaFXImpl implements EventService {

	private Set<ChatterEventListener> chatterEventListeners = new HashSet<>();

	@Override
	public void sendEvent(EventInfo event) {
		if (chatterEventListeners.isEmpty()) {
			List<ChatterEventListener> beans = ChatterApplicationContext.getBeans(ChatterEventListener.class);
			chatterEventListeners.addAll(beans);
		}

		Platform.runLater(() -> {
			for (ChatterEventListener listener : chatterEventListeners) {
				listener.handleEvent(event);
			}
		});
	}

	@Override
	public void registerListener(ChatterEventListener listener) {
		chatterEventListeners.add(listener);
	}

}
