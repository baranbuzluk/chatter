package com.findik.chatter.abstracts;

import java.lang.reflect.Method;
import java.util.List;

public abstract class AbstractEventManager {

	protected final <T> void notifyListeners(Class<T> eventListenerClass, List<T> listeners, Object... params) {
		if (eventListenerClass == null || listeners == null) {
			return;
		}

		final Method method = eventListenerClass.getMethods()[0];
		listeners.parallelStream().forEach(listener -> {
			try {
				method.invoke(listener, params);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
