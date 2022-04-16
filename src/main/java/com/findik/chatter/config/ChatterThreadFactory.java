package com.findik.chatter.config;

import java.util.concurrent.ThreadFactory;

public final class ChatterThreadFactory {

	private ChatterThreadFactory() {
	}

	private static int eventHandlerThreadNum = 0;

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

}
