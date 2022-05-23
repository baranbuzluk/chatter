package com.findik.chatter.core;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.findik.chatter.enums.ConfigPropertiesKey;

@Component
public class ConfigPropertiesServiceImpl implements ConfigPropertiesService {

	private static final String RESOURCES_CONFIG_PROPERTIES = "./resources/config.properties";

	private final Properties properties;

	public ConfigPropertiesServiceImpl() {
		properties = new Properties();
		try (FileInputStream fis = new FileInputStream(RESOURCES_CONFIG_PROPERTIES)) {
			properties.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Path getMessageOutputDirectory() {
		String messageOutputPath = properties.getProperty(ConfigPropertiesKey.MESSAGE_OUTPUT_PATH.getKey());
		return Path.of(messageOutputPath);
	}

	@Override
	public String getClientIp() {
		return properties.getProperty(ConfigPropertiesKey.CLIENT_IP.getKey());
	}

	@Override
	public String getClientPort() {
		return properties.getProperty(ConfigPropertiesKey.CLIENT_PORT.getKey());
	}
}
