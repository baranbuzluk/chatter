package com.chatter.core;

import java.nio.file.Path;

public interface ChatterConfiguration {

	Path getMessageOutputDirectory();

	String getClientPort();

	String getClientIp();

	Integer getConnectTimeout();

}
