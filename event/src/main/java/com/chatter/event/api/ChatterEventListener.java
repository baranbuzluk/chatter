package com.chatter.event.api;

import com.chatter.event.data.EventInfo;

public interface ChatterEventListener extends java.util.EventListener {

	void handleEvent(EventInfo eventInfo);
}
