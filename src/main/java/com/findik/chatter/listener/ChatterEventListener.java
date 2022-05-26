package com.findik.chatter.listener;

import java.util.EventListener;

public interface ChatterEventListener extends EventListener {

	void handleEvent(EventInfo eventInfo);
}
