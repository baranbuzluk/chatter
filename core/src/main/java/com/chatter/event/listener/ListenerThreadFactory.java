package com.chatter.event.listener;

import java.util.concurrent.ThreadFactory;

public final class ListenerThreadFactory {

	private ListenerThreadFactory() {
	}

	private static int eventHandlerThreadNum;

	public static ThreadFactory newEventHandlerThread() {
		return r -> {
			Runnable runnable = () -> {
				r.run();
				eventHandlerThreadNum--;
			};
			Thread thread = new Thread(runnable);
			thread.setName("Event Handler Thread - " + eventHandlerThreadNum++);
			thread.setDaemon(true);
			return thread;
		};
	}

	public static Thread newDaemonThread(String name, Runnable runnable) {
		Thread thread = new Thread(runnable, name);
		thread.setDaemon(true);
		return thread;
	}
}
