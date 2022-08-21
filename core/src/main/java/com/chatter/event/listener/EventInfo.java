package com.chatter.event.listener;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class EventInfo extends AbstractMap<EventProperties, Object> {

	private final Event event;

	private Set<Map.Entry<EventProperties, Object>> entrySet;

	public EventInfo(Event event) {
		this.event = event;
	}

	public Event getEvent() {
		return event;
	}

	@Override
	public Set<Entry<EventProperties, Object>> entrySet() {
		if (entrySet == null)
			entrySet = new HashSet<>();
		return entrySet;
	}

	@Override
	public Object put(EventProperties key, Object value) {
		SimpleEntry<EventProperties, Object> e = new SimpleEntry<>(key, value);
		entrySet().add(e);
		return e;
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		return Objects.hash(event, entrySet);
	}

}
