package com.chatter.service;

import java.util.List;

import com.chatter.dto.MessageDto;

public interface MessageService {

	MessageDto sendMessage(String message, String recipientUsername);

	List<MessageDto> getAllMessages();

}
