module chatter {

	exports com.chatter;
	exports com.chatter.client.enums;
	exports com.chatter.client.main;
	exports com.chatter.client.controller.chat;
	exports com.chatter.client.controller.login;
	exports com.chatter.client.controller.registration;
	exports com.chatter.client.connect;
	exports com.chatter.client.session;

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
	requires transitive core;
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive javafx.base;
	requires transitive org.hibernate.orm.core;
	requires transitive java.activation;
	requires transitive org.slf4j;

	opens com.chatter.client.main;
	opens com.chatter.client.controller.chat;
	opens com.chatter.client.controller.login;
	opens com.chatter.client.controller.registration;
	opens com.chatter.client.enums;
	opens com.chatter.client.connect;
	opens com.chatter.client.session;
	opens com.chatter;
}