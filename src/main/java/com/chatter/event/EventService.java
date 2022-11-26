package com.chatter.event;

public interface EventService {

	void sendEvent(EventInfo event);

	void registerListener(ChatterEventListener listener);

}
