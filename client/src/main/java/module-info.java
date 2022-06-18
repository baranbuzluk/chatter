module chatter {
	exports com.chatter.client.enums;
	exports com.chatter.client.main;
	exports com.chatter.client.controller.chat;
	exports com.chatter.client.controller.login;
	exports com.chatter.client.controller.registration;
	exports com.chatter.client.config;
	exports com.chatter.client.connect;

	requires transitive java.sql;
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
	requires transitive listener;
	requires transitive data;
	requires transitive javafx.graphics;
	requires transitive core;
	requires javafx.controls;

	opens com.chatter.client.main;
	opens com.chatter.client.controller.chat;
	opens com.chatter.client.controller.login;
	opens com.chatter.client.controller.registration;
	opens com.chatter.client.enums;
	opens com.chatter.client.config;
	opens com.chatter.client.connect;

}