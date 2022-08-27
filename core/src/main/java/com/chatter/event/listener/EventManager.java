package com.chatter.event.listener;

public interface EventManager {

	void sendEvent(EventInfo event);

	void stopNotifyingListeners();

	void startNotifyingListeners();

	void registerListener(ChatterEventListener listener);

	void unregisterListener(ChatterEventListener listener);
}