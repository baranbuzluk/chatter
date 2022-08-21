module core {

	exports com.chatter.abstracts;
	exports com.chatter.configuration;
	exports com.chatter.entity;
	exports com.chatter.event.listener;
	exports com.chatter.repository;
	exports com.chatter.socket;
	exports com.chatter.util;

	opens com.chatter.abstracts;
	opens com.chatter.configuration;
	opens com.chatter.entity;
	opens com.chatter.event.listener;
	opens com.chatter.repository;
	opens com.chatter.socket;
	opens com.chatter.util;

	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;

	requires transitive com.fasterxml.jackson.dataformat.xml;
	requires transitive com.fasterxml.jackson.core;
	requires transitive com.fasterxml.jackson.databind;
	requires transitive com.fasterxml.jackson.datatype.jsr310;

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