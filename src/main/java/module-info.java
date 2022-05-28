module chatter {
	exports com.chatter.core;
	exports com.chatter.config;
	exports com.chatter.entity;
	exports com.chatter.enums;
	exports com.chatter.listener;
	exports com.chatter.main;
	exports com.chatter.repository;
	exports com.chatter.controller.chat;
	exports com.chatter.controller.login;
	exports com.chatter.socket;
	exports com.chatter.xml;

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
	requires transitive xstream;

	opens com.chatter.core;
	opens com.chatter.config;
	opens com.chatter.entity;
	opens com.chatter.main;
	opens com.chatter.repository;
	opens com.chatter.controller.chat;
	opens com.chatter.controller.login;
	opens com.chatter.socket;
	opens com.chatter.xml;
	opens com.chatter.listener;
	opens com.chatter.enums;

}