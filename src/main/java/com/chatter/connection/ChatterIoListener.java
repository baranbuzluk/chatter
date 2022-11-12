package com.chatter.connection;

import java.util.List;

public interface ChatterIoListener {

	void messageReceived(List<String> messages);
}
