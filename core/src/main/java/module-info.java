module core {
	exports com.chatter.core;

	opens com.chatter.core;

	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;
}