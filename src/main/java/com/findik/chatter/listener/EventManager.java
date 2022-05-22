package com.findik.chatter.listener;

public interface EventManager {

	void sendEvent(EventInfo event);

	void stopNotifyingListeners();

	void startNotifyingListeners();
}
