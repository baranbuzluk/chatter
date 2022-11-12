package com.chatter.configuration;

import java.io.File;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import javafx.embed.swing.JFXPanel;

@Configuration
@ComponentScan(basePackages = "com.chatter")
@Import({ DatabaseConfig.class })
@PropertySource("classpath:application.properties")

public class ChatterApplicationContext {

	private static final Logger LOG = LoggerFactory.getLogger(ChatterApplicationContext.class);

	private static AnnotationConfigApplicationContext APPLICATION_CONTEXT;

	public static void start() {
		if (APPLICATION_CONTEXT == null) {
			new JFXPanel();
			APPLICATION_CONTEXT = new AnnotationConfigApplicationContext(ChatterApplicationContext.class);
		}
	}

	public static void close() {
		if (APPLICATION_CONTEXT != null) {
			APPLICATION_CONTEXT.close();
		}
	}

	public static <T> T getBean(Class<T> type) {
		return APPLICATION_CONTEXT.getBean(type);
	}

	@Bean
	@Scope("prototype")
	public Logger logger(InjectionPoint ip) {
		Class<?> clazz = ip.getMember().getDeclaringClass();
		String msg = MessageFormat.format("Initialized Logger for {0}", clazz);
		LOG.info(msg);
		return LoggerFactory.getLogger(clazz);
	}

	@Bean
	public File getMessageFolder(@Value(value = "${message.folder}") String messageFolderPath) {
		File file = new File(messageFolderPath);
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}

}
