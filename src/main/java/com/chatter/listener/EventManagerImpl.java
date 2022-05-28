package com.chatter.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Component;

import com.chatter.factory.ChatterThreadFactory;

@Component
public class EventManagerImpl implements EventManager {

	private List<ChatterEventListener> chatterEventListeners;

	private BlockingQueue<EventInfo> events;

	private ExecutorService eventHandlerThread;

	private Thread eventListenerThread;

	private boolean runLoop;

	public EventManagerImpl() {
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
				chatterEventListeners.forEach(e -> eventHandlerThread.execute(() -> e.handleEvent(eventInfo)));
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
			eventListenerThread = ChatterThreadFactory.newDaemonThread("Event Listener Thread",
					this::runNotifyingEventListenersTask);
			eventListenerThread.start();
		}
	}

	@Override
	public synchronized void registerListener(ChatterEventListener listener) {
		if (chatterEventListeners == null)
			chatterEventListeners = new ArrayList<>();

		ChatterEventListener o = Objects.requireNonNull(listener, "ChatterEventListener can not be null!");
		if (!chatterEventListeners.contains(o))
			chatterEventListeners.add(o);
	}

	@Override
	public synchronized void unregisterListener(ChatterEventListener listener) {
		if (chatterEventListeners == null || chatterEventListeners.isEmpty())
			return;
		ChatterEventListener o = Objects.requireNonNull(listener, "ChatterEventListener can not be null!");
		chatterEventListeners.remove(o);
	}

}
