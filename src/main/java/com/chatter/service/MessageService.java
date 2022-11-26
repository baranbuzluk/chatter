package com.chatter.service;

import java.util.List;

import com.chatter.dto.MessageDto;

public interface MessageService {

	boolean sendMessage(MessageDto message);

	List<MessageDto> getAllMessages();

}
