package com.chatter.communication;

import java.util.List;

import com.chatter.dto.MessageDto;

public interface CommunicationManager {

	List<String> getActiveHostAddressList();

	String getHostAddress();

	boolean sendMessage(MessageDto message);
}
