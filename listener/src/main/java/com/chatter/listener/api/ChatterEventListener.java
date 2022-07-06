package com.chatter.listener.api;

public interface ChatterEventListener extends java.util.EventListener {

	void handleEvent(EventInfo eventInfo);
}
