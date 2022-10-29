package com.chatter.event.data;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class EventInfo extends AbstractMap<ChatterEventProperties, Object> {

	private final ChatterEvent event;

	private Set<Map.Entry<ChatterEventProperties, Object>> entrySet;

	public EventInfo(ChatterEvent event) {
		this.event = event;
	}

	public ChatterEvent getEvent() {
		return event;
	}

	@Override
	public Set<Entry<ChatterEventProperties, Object>> entrySet() {
		if (entrySet == null)
			entrySet = new HashSet<>();
		return entrySet;
	}

	@Override
	public Object put(ChatterEventProperties key, Object value) {
		SimpleEntry<ChatterEventProperties, Object> e = new SimpleEntry<>(key, value);
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
