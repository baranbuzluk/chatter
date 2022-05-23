package com.findik.chatter.core;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.findik.chatter.enums.ChatterConfigurationKey;

@Component
public class ChatterConfigurationImpl implements ChatterConfiguration {

	private static final String RESOURCES_CONFIG_PROPERTIES = "./resources/config.properties";

	private final Properties properties;

	public ChatterConfigurationImpl() {
		properties = new Properties();
		try (FileInputStream fis = new FileInputStream(RESOURCES_CONFIG_PROPERTIES)) {
			properties.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Path getMessageOutputDirectory() {
		String messageOutputPath = properties.getProperty(ChatterConfigurationKey.MESSAGE_OUTPUT_PATH.getKey());
		return Path.of(messageOutputPath);
	}

	@Override
	public String getClientIp() {
		return properties.getProperty(ChatterConfigurationKey.CLIENT_IP.getKey());
	}

	@Override
	public String getClientPort() {
		return properties.getProperty(ChatterConfigurationKey.CLIENT_PORT.getKey());
	}

	@Override
	public Integer getConnectTimeout() {
		String value = properties.getProperty(ChatterConfigurationKey.CONNECT_TIMEOUT.getKey());
		if (value == null)
			throw new PropertiesKeyNotFoundException(ChatterConfigurationKey.CONNECT_TIMEOUT.getKey());
		return Integer.parseInt(value);
	}
}
