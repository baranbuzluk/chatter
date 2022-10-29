module data {
	opens com.chatter.data.util;
	opens com.chatter.data.entity;
	opens com.chatter.data.repository;

	exports com.chatter.data.util;
	exports com.chatter.data.entity;
	exports com.chatter.data.repository;

	requires transitive com.fasterxml.jackson.annotation;
	requires transitive com.fasterxml.jackson.core;
	requires transitive com.fasterxml.jackson.databind;
	requires transitive com.fasterxml.jackson.dataformat.xml;
	requires transitive com.fasterxml.jackson.datatype.jsr310;
	requires transitive java.persistence;
	requires transitive org.hibernate.orm.core;
	requires transitive spring.context;
	requires transitive spring.core;
	requires transitive spring.data.commons;
	requires transitive spring.data.jpa;
}