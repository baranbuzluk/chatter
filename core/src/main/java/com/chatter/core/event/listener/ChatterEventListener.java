package com.chatter.core.event.listener;

public interface ChatterEventListener extends java.util.EventListener {

	void handleEvent(EventInfo eventInfo);
}
