package com.findik.chatter.listener.api;

import com.findik.chatter.listener.impl.EventInfo;

public interface EventManager {

	void sendEvent(EventInfo eventInfo);

	void stopNotifyingListeners();

	void startNotifyingListeners();
}
