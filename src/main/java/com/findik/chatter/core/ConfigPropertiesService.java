package com.findik.chatter.core;

import java.nio.file.Path;

public interface ConfigPropertiesService {

	Path getMessageOutputDirectory();

	String getClientPort();

	String getClientIp();

}
