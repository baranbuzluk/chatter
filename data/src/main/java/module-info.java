module data {
	exports com.chatter.data.entity;
	exports com.chatter.data.repository;
	exports com.chatter.data.config;
	exports com.chatter.data.util;

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

	opens com.chatter.data.entity;
	opens com.chatter.data.repository;
	opens com.chatter.data.config;
	opens com.chatter.data.util;

}