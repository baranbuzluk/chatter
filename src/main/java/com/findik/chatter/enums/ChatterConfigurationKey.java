package com.findik.chatter.enums;

public enum ChatterConfigurationKey {

	MESSAGE_OUTPUT_PATH("message.output.path"), CLIENT_PORT("client.port"), CLIENT_IP("client.ip"),
	CONNECT_TIMEOUT("connect_timeout");

	private String key;

	private ChatterConfigurationKey(String key) {
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
