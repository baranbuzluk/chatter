package com.chatter.event;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import javafx.application.Platform;

@Component
class EventManagerJavaFXImpl implements EventManager {

	private Set<ChatterEventListener> chatterEventListeners = new HashSet<>();

	@Override
	public void sendEvent(EventInfo event) {
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
