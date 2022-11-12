module chatter {
	exports com.chatter.application.configuration;
	exports com.chatter;

	opens com.chatter.application.configuration;

	opens com.chatter;

	requires transitive java.desktop;
	requires transitive java.persistence;
	requires transitive java.sql;
	requires transitive javafx.base;
	requires transitive javafx.graphics;
	requires transitive javafx.swing;
	requires transitive org.slf4j;
	requires transitive spring.beans;
	requires transitive spring.context;
	requires transitive spring.core;
	requires transitive spring.data.commons;
	requires transitive spring.data.jpa;
	requires transitive spring.jdbc;
	requires transitive spring.orm;
	requires transitive spring.tx;
	requires transitive view.service;
	requires transitive java.instrument;

}