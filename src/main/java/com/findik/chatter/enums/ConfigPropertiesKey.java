package com.findik.chatter.enums;

public enum ConfigPropertiesKey {

	MESSAGE_OUTPUT_PATH("message.output.key"), CLIENT_PORT("client.port"), CLIENT_IP("client.ip");

	private String key;

	private ConfigPropertiesKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	@Override
	public String toString() {
		return getKey();
	}

}
