package com.findik.chatter.core;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class ConfigPropertiesServiceImpl implements ConfigPropertiesService {

	private static final String MESSAGE_OUTPUT_PATH_KEY = "message.output.path";

	private static final String RESOURCES_CONFIG_PROPERTIES = "./resources/config.properties";

	private Properties properties = new Properties();

	public ConfigPropertiesServiceImpl() {
		setPropertiesInputStream();
	}

	private void setPropertiesInputStream() {
		try (FileInputStream fis = new FileInputStream(RESOURCES_CONFIG_PROPERTIES)) {
			properties.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Path getMessageOutputDirectory() {
		String messageOutputPath = properties.getProperty(MESSAGE_OUTPUT_PATH_KEY);
		return Path.of(messageOutputPath);
	}
}
