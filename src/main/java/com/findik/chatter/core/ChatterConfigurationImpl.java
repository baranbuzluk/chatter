package com.findik.chatter.core;

import java.nio.file.Path;

import org.springframework.stereotype.Component;

@Component
public class ChatterConfigurationImpl implements ChatterConfiguration {

	@Override
	public Path getMessageOutputDirectory() {
		return Path.of("C:/CHATTER/");
	}

	@Override
	public String getClientIp() {
		return "127.0.0.1";
	}

	@Override
	public String getClientPort() {
		return "9999";
	}

	@Override
	public Integer getConnectTimeout() {
		return 1000;
	}
}
