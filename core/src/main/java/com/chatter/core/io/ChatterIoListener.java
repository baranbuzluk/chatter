package com.chatter.core.io;

import java.util.List;

public interface ChatterIoListener {

	void messageReceived(List<String> messages);
}
