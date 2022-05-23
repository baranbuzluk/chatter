package com.findik.chatter.core;

public class PropertiesKeyNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8576140659829581403L;

	public PropertiesKeyNotFoundException(String key) {
		super(key + " not found in properties file.");
	}

}
