module view.service {

	opens com.chatter.view.service;

	exports com.chatter.view.service;
	exports com.chatter.view.service.impl;

	requires transitive event;
	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;
	requires transitive org.slf4j;
	requires transitive spring.beans;
	requires transitive spring.context;
	requires transitive data;
}