module listener {
	exports com.chatter.listener.api;
	exports com.chatter.listener.impl;

	opens com.chatter.listener.api;
	opens com.chatter.listener.impl;

	requires spring.context;
}