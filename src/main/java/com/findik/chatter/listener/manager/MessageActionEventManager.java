package com.findik.chatter.listener.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.abstracts.AbstractEventManager;
import com.findik.chatter.entity.Message;
import com.findik.chatter.listener.api.IMessageAddedEventListener;

@Component
public class MessageActionEventManager extends AbstractEventManager {

	@Autowired
	private List<IMessageAddedEventListener> messageAddedEventListeners;

	public void notifyMessageAddedEventListeners(Message addedMessage) {
		notifyListeners(IMessageAddedEventListener.class, messageAddedEventListeners, addedMessage);
	}

}
