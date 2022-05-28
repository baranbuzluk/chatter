package com.chatter.listener.api;

public interface EventListener extends java.util.EventListener {

	void handleEvent(EventInfo eventInfo);
}
