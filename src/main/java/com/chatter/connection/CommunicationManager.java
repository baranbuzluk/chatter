package com.chatter.connection;

import java.util.List;

import com.chatter.data.entity.Message;

public interface CommunicationManager {

	List<String> getActiveHostAddressList();

	String getHostAddress();

	boolean sendMessage(Message message);
}
