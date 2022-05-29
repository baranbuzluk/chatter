module core {
	exports com.chatter.core;
	exports com.chatter.core.util;

	opens com.chatter.core;
	opens com.chatter.core.util;

	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;

	requires transitive com.fasterxml.jackson.dataformat.xml;
	requires transitive com.fasterxml.jackson.core;
	requires transitive com.fasterxml.jackson.databind;
	requires transitive com.fasterxml.jackson.datatype.jsr310;
}