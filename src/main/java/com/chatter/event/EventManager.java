package com.chatter.event;

public interface EventManager {

	void sendEvent(EventInfo event);

	void registerListener(ChatterEventListener listener);

}
