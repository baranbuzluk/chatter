module event {

	exports com.chatter.event.api;
	exports com.chatter.event.data;

	opens com.chatter.event.api;
	opens com.chatter.event.data;
	opens com.chatter.event.impl;

	requires transitive spring.context;
	requires spring.beans;

}