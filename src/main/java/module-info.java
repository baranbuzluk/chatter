module chatter {
	exports com.findik.chatter.main;
	exports com.findik.chatter.view;
	exports com.findik.chatter.entity;
	exports com.findik.chatter.config;
	exports com.findik.chatter.repository;
	exports com.findik.chatter.window;

	requires transitive java.sql;
	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;
	requires transitive spring.data.jpa;
	requires transitive spring.jdbc;
	requires transitive spring.orm;
	requires transitive spring.context;
	requires transitive spring.tx;
	requires transitive java.persistence;
	requires transitive spring.beans;
	requires transitive spring.data.commons;
	requires transitive spring.boot.autoconfigure;
	requires transitive spring.core;
	requires transitive java.annotation;
	requires transitive org.hibernate.orm.core;
	requires transitive java.instrument;
	requires xstream;

	opens com.findik.chatter.main;
	opens com.findik.chatter.view;
	opens com.findik.chatter.entity;
	opens com.findik.chatter.repository;
	opens com.findik.chatter.config;
	opens com.findik.chatter.window;
}