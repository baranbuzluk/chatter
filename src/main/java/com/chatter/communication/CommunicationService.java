package com.chatter.communication;

import com.chatter.dto.MessageDto;

public interface CommunicationService {

	boolean sendMesssage(MessageDto messageDto, String hostAddress);

	void setMessageListener(MessageListener messageListener);

}
