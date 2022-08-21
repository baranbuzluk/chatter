package com.chatter.event.listener;

public interface ChatterEventListener extends java.util.EventListener {

	void handleEvent(EventInfo eventInfo);
}
