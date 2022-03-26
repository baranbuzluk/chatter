package com.findik.chatter.listener;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.abstracts.IApplicationEventListenerManager;

@Component
public class ApplicationEventListenerManager implements IApplicationEventListenerManager {

	@Autowired
	List<IStartedApplicationEventListener> startedApplicationEventListeners;

	@Override
	public void notifyStartedApplicationEventListeners() {
		startedApplicationEventListeners.parallelStream().forEach(e -> e.startedApplicationEvent());
	}

}
