package com.chatter.core.event.listener;

public interface EventManager {

	void sendEvent(EventInfo event);

	void registerListener(ChatterEventListener listener);

}
