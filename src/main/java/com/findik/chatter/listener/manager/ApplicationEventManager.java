package com.findik.chatter.listener.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.abstracts.AbstractEventManager;
import com.findik.chatter.listener.api.IApplicationStartedEventListener;

@Component
public class ApplicationEventManager extends AbstractEventManager {

	@Autowired
	List<IApplicationStartedEventListener> startedApplicationEventListeners;

	public void notifyStartedApplicationEventListeners() {
		notifyListeners(IApplicationStartedEventListener.class, startedApplicationEventListeners);
	}

}
