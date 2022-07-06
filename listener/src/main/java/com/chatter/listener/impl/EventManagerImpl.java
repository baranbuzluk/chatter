package com.chatter.listener.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Component;

import com.chatter.listener.api.ChatterEventListener;
import com.chatter.listener.api.EventInfo;
import com.chatter.listener.api.EventManager;

@Component
public class EventManagerImpl implements EventManager {

	private List<ChatterEventListener> chatterEventListeners;

	private BlockingQueue<EventInfo> events;

	private ExecutorService eventHandlerThread;

	private Thread eventListenerThread;

	private boolean runLoop;

	public EventManagerImpl() {
		chatterEventListeners = new ArrayList<>();
		events = new LinkedBlockingQueue<>();
		eventHandlerThread = Executors.newCachedThreadPool(ListenerThreadFactory.newEventHandlerThread());
		startNotifyingListeners();
	}

	@Override
	public void sendEvent(EventInfo eventInfo) {
		EventInfo event = Objects.requireNonNull(eventInfo, "eventInfo must not be null!");
		events.add(event);
	}

	private void runNotifyingEventListenersTask() {
		try {
			while (runLoop) {
				EventInfo eventInfo = events.take();
				notifyEventListeners(eventInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void notifyEventListeners(EventInfo eventInfo) {
		for (ChatterEventListener eventListener : chatterEventListeners) {
			Runnable runnable = () -> eventListener.handleEvent(eventInfo);
			eventHandlerThread.execute(runnable);
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
	public synchronized void registerListener(ChatterEventListener listener) {
		ChatterEventListener o = Objects.requireNonNull(listener, "listener must not be null!");
		if (!chatterEventListeners.contains(o))
			chatterEventListeners.add(o);
	}

	@Override
	public synchronized void unregisterListener(ChatterEventListener listener) {
		if (chatterEventListeners.isEmpty())
			return;
		ChatterEventListener o = Objects.requireNonNull(listener, "listener must not be null!");
		chatterEventListeners.remove(o);
	}

}
