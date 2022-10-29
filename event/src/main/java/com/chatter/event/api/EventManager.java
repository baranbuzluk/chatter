package com.chatter.event.api;

import com.chatter.event.data.EventInfo;

public interface EventManager {

	void sendEvent(EventInfo event);

	void registerListener(ChatterEventListener listener);

}
