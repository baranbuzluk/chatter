package com.findik.chatter.abstracts.window;

public class AbstractWindow implements IWindow {

	protected AbstractWindowController<?> windowController;

	@Override
	public IWindowController<?> getWindowController() {
		return windowController;
	}

}
