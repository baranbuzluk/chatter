package com.findik.chatter.abstracts.window;

import javafx.scene.layout.Pane;

public interface IWindowController<T extends Pane> {

	T getPane();
}
