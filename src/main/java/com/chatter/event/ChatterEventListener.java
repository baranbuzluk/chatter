package com.chatter.event;

public interface ChatterEventListener extends java.util.EventListener {

	void handleEvent(EventInfo eventInfo);
}
