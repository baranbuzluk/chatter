package com.findik.chatter.listener.impl;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.config.ChatterThreadFactory;
import com.findik.chatter.listener.api.EventListener;
import com.findik.chatter.listener.api.EventManager;

@Component
public class EventManagerImpl implements EventManager {

	@Autowired
	private List<EventListener> eventListeners;

	private BlockingQueue<EventInfo> events;

	private ExecutorService eventHandlerThread;

	private Thread eventListenerThread;

	public EventManagerImpl() {
		events = new LinkedBlockingQueue<>();
		eventHandlerThread = Executors.newCachedThreadPool(ChatterThreadFactory.newEventHandlerThread());
		startNotifyingListeners();
	}

	@Override
	public void sendEvent(EventInfo eventInfo) {
		events.add(eventInfo);
	}

	private void notifyingEventListenersTask() {
		try {
			while (true) {
				EventInfo eventInfo = events.take();
				eventListeners.forEach(t -> eventHandlerThread.execute(() -> t.handleEvent(eventInfo)));
			}
		} catch (InterruptedException e) {
			System.err.println("Stopped Event Listener Thread..!");
		}
	}

	@Override
	public void stopNotifyingListeners() {
		if (eventListenerThread != null) {
			eventListenerThread.interrupt();
			eventListenerThread = null;
		}
	}

	@Override
	public void startNotifyingListeners() {
		System.err.println("startNotifyingListeners()" + Thread.currentThread().getName());
		if (eventListenerThread == null) {
			eventListenerThread = new Thread(this::notifyingEventListenersTask, "Event Listener Thread");
			eventListenerThread.setDaemon(true);
			eventListenerThread.start();
			System.err.println("Started Event Listener Thread..!");
		}
	}

}
