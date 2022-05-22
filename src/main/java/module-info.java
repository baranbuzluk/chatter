module chatter {
	exports com.findik.chatter.core;
	exports com.findik.chatter.config;
	exports com.findik.chatter.entity;
	exports com.findik.chatter.enums;
	exports com.findik.chatter.listener;
	exports com.findik.chatter.main;
	exports com.findik.chatter.repository;
	exports com.findik.chatter.controller.chat;
	exports com.findik.chatter.controller.login;
	exports com.findik.chatter.xml;

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

	opens com.findik.chatter.core;
	opens com.findik.chatter.config;
	opens com.findik.chatter.entity;
	opens com.findik.chatter.main;
	opens com.findik.chatter.repository;
	opens com.findik.chatter.controller.chat;
	opens com.findik.chatter.controller.login;
	opens com.findik.chatter.xml;
	opens com.findik.chatter.listener;
	opens com.findik.chatter.enums;

}