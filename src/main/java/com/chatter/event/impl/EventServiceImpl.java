package com.chatter.event.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.chatter.event.ChatterEventListener;
import com.chatter.event.EventInfo;
import com.chatter.event.EventService;

class EventServiceImpl implements EventService {

	private final List<ChatterEventListener> chatterEventListeners = new ArrayList<>();

	private final BlockingQueue<EventInfo> events = new LinkedBlockingQueue<>();

	private Thread thread;

	public EventServiceImpl() {
		startEventListener();
	}

	@Override
	public void sendEvent(EventInfo eventInfo) {
		EventInfo event = Objects.requireNonNull(eventInfo, "eventInfo must not be null!");
		events.add(event);
	}

	@Override
	public synchronized void registerListener(ChatterEventListener listener) {
		ChatterEventListener o = Objects.requireNonNull(listener, "listener must not be null!");
		if (!chatterEventListeners.contains(o))
			chatterEventListeners.add(o);
	}

	private void startEventListener() {
		Runnable runnable = () -> {
			try {
				while (true) {
					EventInfo eventInfo = events.take();
					chatterEventListeners.forEach(listener -> listener.handleEvent(eventInfo));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		};

		thread = new Thread(runnable, "EventManagerImpl");
		thread.setDaemon(true);
		thread.start();
	}

}