package com.chatter.listener.api;

public interface EventManager {

	void sendEvent(EventInfo event);

	void stopNotifyingListeners();

	void startNotifyingListeners();

	void registerListener(EventListener listener);

	void unregisterListener(EventListener listener);
}
