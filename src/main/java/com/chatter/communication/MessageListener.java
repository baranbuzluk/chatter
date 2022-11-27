package com.chatter.communication;

import com.chatter.dto.MessageDto;

public interface MessageListener {

	void receivedMessage(MessageDto messageDto);
}
