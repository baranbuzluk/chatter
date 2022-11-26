package com.chatter.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.dto.MessageDto;
import com.chatter.service.MessageService;

@Component
public class MessageServiceImpl implements MessageService {

	private MessageRepository repository;

	@Autowired
	public MessageServiceImpl(MessageRepository repository) {
		this.repository = repository;
	}

	@Override
	public boolean sendMessage(MessageDto message) {
		MessageFileWriter.XML.write(message);
		Message entity = new Message();
		entity.setContent(message.content);
		return true;
	}

	@Override
	public List<MessageDto> getAllMessages() {
		List<Message> messageEntities = repository.findAll();
		List<MessageDto> messageDtos = new ArrayList<>(messageEntities.size());

		for (Message entity : messageEntities) {
			MessageDto dto = new MessageDto();
			dto.content = entity.getContent();
			dto.createdTime = entity.getCreatedAt();
			dto.recipientUsername = "Test Recipient";
			dto.senderUsername = "Test Sender";
		}

		return messageDtos;
	}

}
