package com.chatter.client.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.chatter.data.config.DatabaseConfig;

@Configuration
@ComponentScan(basePackages = "com.chatter")
@Import({ DatabaseConfig.class })
public class ChatterApplicationContext {

	private static AnnotationConfigApplicationContext annotationConfigApplicationContext;

	public static void start() {
		if (annotationConfigApplicationContext == null) {
			annotationConfigApplicationContext = new AnnotationConfigApplicationContext(
					ChatterApplicationContext.class);
		}
	}

	public static void close() {
		if (annotationConfigApplicationContext != null) {
			annotationConfigApplicationContext.close();
		}
	}

	public static <T> T getBean(Class<T> type) {
		return annotationConfigApplicationContext.getBean(type);
	}

}
