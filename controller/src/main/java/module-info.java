module controller {
	exports com.chatter.controller.chat;
	exports com.chatter.controller.register;
	exports com.chatter.controller.login;
	exports com.chatter.controller.session;

	requires transitive java.sql;
	requires transitive spring.data.jpa;
	requires transitive spring.jdbc;
	requires transitive spring.orm;
	requires transitive spring.context;
	requires transitive spring.tx;
	requires transitive java.persistence;
	requires transitive spring.beans;
	requires transitive spring.data.commons;

	requires transitive spring.core;
	requires transitive java.instrument;
	requires transitive javafx.graphics;
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive javafx.base;
	requires transitive org.hibernate.orm.core;
	requires transitive java.activation;
	requires transitive org.slf4j;
	requires transitive event;
	requires transitive data;
	requires transitive view.service;

}