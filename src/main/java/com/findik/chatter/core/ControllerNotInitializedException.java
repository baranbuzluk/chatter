package com.findik.chatter.core;

public class ControllerNotInitializedException extends RuntimeException {

	private static final long serialVersionUID = -6697198515068531303L;

	public ControllerNotInitializedException() {
		super("Controller could not load!");
	}

}
