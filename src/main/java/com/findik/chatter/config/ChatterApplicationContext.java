package com.findik.chatter.config;

import java.util.logging.Logger;

import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = "com.findik.chatter")
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

	public static ApplicationContext getApplicationContext() {
		return annotationConfigApplicationContext;
	}

	public static <T> T getBean(Class<T> type) {
		return annotationConfigApplicationContext.getBean(type);
	}

	public static <T> T getBean(String name, Class<T> type) {
		return annotationConfigApplicationContext.getBean(name, type);
	}

	@Bean
	@Scope("prototype")
	public Logger produceLogger(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}

}
