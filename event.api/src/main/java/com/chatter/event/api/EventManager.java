package com.chatter.event.api;

public interface EventManager {

	void sendEvent(EventInfo event);

	void registerListener(ChatterEventListener listener);

}
