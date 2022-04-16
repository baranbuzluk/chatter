package com.findik.chatter.listener.api;

import com.findik.chatter.listener.impl.EventInfo;

public interface EventListener {

	void handleEvent(EventInfo eventInfo);
}
