package com.chatter.configuration;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javafx.embed.swing.JFXPanel;

@Configuration
@ComponentScan(basePackages = "com.chatter")
//@Import({ DatabaseConfig.class })
@PropertySource("classpath:application.properties")
public class ChatterApplicationContext {

	private static AnnotationConfigApplicationContext APPLICATION_CONTEXT;

	public static void start() {
		if (APPLICATION_CONTEXT == null) {
			new JFXPanel();
			APPLICATION_CONTEXT = new AnnotationConfigApplicationContext(ChatterApplicationContext.class);
		}
	}

	public static <T> T getBean(Class<T> type) {
		return APPLICATION_CONTEXT.getBean(type);
	}

	public static <T> List<T> getBeans(Class<T> type) {
		return APPLICATION_CONTEXT.getBeanProvider(type).stream().toList();
	}

}
