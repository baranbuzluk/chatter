module core {

	exports com.chatter.core.abstracts;
	exports com.chatter.core.configuration;
	exports com.chatter.core.entity;
	exports com.chatter.core.event.listener;
	exports com.chatter.core.repository;
	exports com.chatter.core.socket;
	exports com.chatter.core.util;

	opens com.chatter.core.abstracts;
	opens com.chatter.core.configuration;
	opens com.chatter.core.entity;
	opens com.chatter.core.event.listener;
	opens com.chatter.core.repository;
	opens com.chatter.core.socket;
	opens com.chatter.core.util;

	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;

	requires transitive com.fasterxml.jackson.core;
	requires transitive com.fasterxml.jackson.databind;
	requires transitive com.fasterxml.jackson.datatype.jsr310;
	requires transitive com.fasterxml.jackson.annotation;
	requires transitive com.fasterxml.jackson.dataformat.xml;

	requires transitive java.persistence;
	requires transitive spring.context;
	requires transitive spring.core;
	requires transitive spring.data.commons;
	requires transitive spring.data.jpa;
	requires transitive spring.orm;
	requires transitive java.sql;
	requires transitive spring.jdbc;
	requires transitive spring.tx;
	requires transitive spring.beans;

}