package com.findik.chatter.listener;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.factory.ChatterThreadFactory;

@Component
public class EventManagerImpl implements EventManager {

	private List<EventListener> eventListeners;

	private BlockingQueue<EventInfo> events;

	private ExecutorService eventHandlerThread;

	private Thread eventListenerThread;

	private boolean runLoop;

	@Autowired
	public EventManagerImpl(List<EventListener> eventListeners) {
		this.eventListeners = eventListeners;
		events = new LinkedBlockingQueue<>();
		eventHandlerThread = Executors.newCachedThreadPool(ChatterThreadFactory.newEventHandlerThread());
		startNotifyingListeners();
	}

	@Override
	public void sendEvent(EventInfo eventInfo) {
		events.add(eventInfo);
	}

	private void runNotifyingEventListenersTask() {
		try {
			while (runLoop) {
				EventInfo eventInfo = events.take();
				eventListeners.forEach(e -> eventHandlerThread.execute(() -> e.handleEvent(eventInfo)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stopNotifyingListeners() {
		if (eventListenerThread != null) {
			runLoop = false;
			eventListenerThread = null;
		}
	}

	@Override
	public void startNotifyingListeners() {
		if (eventListenerThread == null) {
			runLoop = true;
			eventListenerThread = ChatterThreadFactory.newDaemonThread("Event Listener Thread",
					this::runNotifyingEventListenersTask);
			eventListenerThread.start();
		}
	}

}
