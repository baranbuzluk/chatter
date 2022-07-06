package com.chatter.listener.api;

public interface EventManager {

	void sendEvent(EventInfo event);

	void stopNotifyingListeners();

	void startNotifyingListeners();

	void registerListener(ChatterEventListener listener);

	void unregisterListener(ChatterEventListener listener);
}
