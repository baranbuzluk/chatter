package com.findik.chatter.listener.api;

import com.findik.chatter.entity.Message;

public interface IMessageAddedEventListener {
	void updateMessageAddedEvent(Message addedMessage);
}
