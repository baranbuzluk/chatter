package com.chatter.connection;

import java.util.List;

public interface CommunicationManager {

	List<String> getActiveHostAddressList();

	CommunicationChannel connectToHostAddress(String hostAddress);
}
