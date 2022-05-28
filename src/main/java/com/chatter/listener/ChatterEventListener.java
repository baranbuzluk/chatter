package com.chatter.listener;

import java.util.EventListener;

public interface ChatterEventListener extends EventListener {

	void handleEvent(EventInfo eventInfo);
}
