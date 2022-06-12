package com.chatter.listener.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Component;

import com.chatter.listener.api.EventInfo;
import com.chatter.listener.api.EventListener;
import com.chatter.listener.api.EventManager;

@Component
public class EventManagerImpl implements EventManager {

	private List<EventListener> chatterEventListeners;

	private BlockingQueue<EventInfo> events;

	private ExecutorService eventHandlerThread;

	private Thread eventListenerThread;

	private boolean runLoop;

	public EventManagerImpl() {
		events = new LinkedBlockingQueue<>();
		eventHandlerThread = Executors.newCachedThreadPool(ListenerThreadFactory.newEventHandlerThread());
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
				for (EventListener eventListener : chatterEventListeners) {
					Runnable runnable = () -> eventListener.handleEvent(eventInfo);
					eventHandlerThread.execute(runnable);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stopNotifyingListeners() {
		if (eventListenerThread != null && runLoop) {
			runLoop = false;
			eventListenerThread = null;
		}
	}

	@Override
	public void startNotifyingListeners() {
		if (eventListenerThread == null && !runLoop) {
			runLoop = true;
			eventListenerThread = ListenerThreadFactory.newDaemonThread("Event Listener Thread",
					this::runNotifyingEventListenersTask);
			eventListenerThread.start();
		}
	}

	@Override
	public synchronized void registerListener(EventListener listener) {
		if (chatterEventListeners == null)
			chatterEventListeners = new ArrayList<>();

		EventListener o = Objects.requireNonNull(listener, "ChatterEventListener can not be null!");
		if (!chatterEventListeners.contains(o))
			chatterEventListeners.add(o);
	}

	@Override
	public synchronized void unregisterListener(EventListener listener) {
		if (chatterEventListeners == null || chatterEventListeners.isEmpty())
			return;
		EventListener o = Objects.requireNonNull(listener, "ChatterEventListener can not be null!");
		chatterEventListeners.remove(o);
	}

}
