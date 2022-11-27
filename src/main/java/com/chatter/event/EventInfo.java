package com.chatter.event;

import java.util.EnumMap;
import java.util.Map;

public class EventInfo {

	public final ChatterEvent event;

	private Map<Variable, Object> variables;

	public EventInfo(ChatterEvent event) {
		this.event = event;
	}

	public void putVariable(Variable variable, Object value) {
		if (variables == null) {
			variables = new EnumMap<>(Variable.class);
		}
		variables.put(variable, value);
	}

	public Object getVariable(Variable variable) {
		if (variables == null)
			return null;

		return variables.get(variable);
	}

}
