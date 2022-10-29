module connection {

	exports com.chatter.connection;

	opens com.chatter.connection;

	requires transitive event;
	requires transitive org.slf4j;
	requires transitive spring.context;
}