package com.chatter.event.api;

public interface ChatterEventListener extends java.util.EventListener {

	void handleEvent(EventInfo eventInfo);
}
