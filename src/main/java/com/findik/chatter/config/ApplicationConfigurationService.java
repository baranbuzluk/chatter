package com.findik.chatter.config;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.findik.chatter.abstracts.IApplicationConfigurationService;

@Component
public class ApplicationConfigurationService implements IApplicationConfigurationService {

	private static final String MESSAGE_OUTPUT_PATH = "message.output.path";

	private static final String RESOURCES_CONFIG_PROPERTIES = "./resources/config.properties";

	private Properties properties = new Properties();

	public ApplicationConfigurationService() {
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
		String messageOutputPath = properties.getProperty(MESSAGE_OUTPUT_PATH);
		return Path.of(messageOutputPath);
	}
}
