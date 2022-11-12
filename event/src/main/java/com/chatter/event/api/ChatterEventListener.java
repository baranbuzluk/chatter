package com.chatter.event.api;

import com.chatter.event.data.EventInfo;

public interface ChatterEventListener {

	void handleEvent(EventInfo eventInfo);
}
