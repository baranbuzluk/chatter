package com.chatter.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.dto.MessageDto;
import com.chatter.event.ChatterEvent;
import com.chatter.event.ChatterEventListener;
import com.chatter.event.EventInfo;
import com.chatter.event.EventService;
import com.chatter.event.Variable;
import com.chatter.post.Post;
import com.chatter.post.PostListener;
import com.chatter.post.PostService;
import com.chatter.service.MessageService;

@Component
class MessageServiceImpl implements MessageService, ChatterEventListener, PostListener {

	private MessageRepository messageRepository;

	private AccountRepository accountRepository;

	private EventService eventService;

	private PostService communicationService;

	private Account loggedInAccount;

	@Autowired
	public MessageServiceImpl(MessageRepository messageRepository, AccountRepository accountRepository,
			EventService eventService, PostService communicationService) {
		this.messageRepository = messageRepository;
		this.accountRepository = accountRepository;
		this.eventService = eventService;
		this.communicationService = communicationService;
		this.communicationService.addPostListener(this);

	}

	@Override
	public MessageDto sendMessage(String message, String recipientUsername) {
		Account senderAccount = loggedInAccount;
		Account recipientAccount = accountRepository.findByUsername(recipientUsername);

		Message entity = new Message();
		entity.setSenderAccount(senderAccount);
		entity.setRecipientAccount(recipientAccount);
		entity.setContent(message);
		messageRepository.saveAndFlush(entity);

		MessageDto messageDto = new MessageDto();
		messageDto.content = message;
		messageDto.recipientUsername = recipientUsername;
		messageDto.senderUsername = loggedInAccount.getUsername();
		messageDto.createdTime = entity.getCreatedAt();

		communicationService.sendPost(messageDto, "localhost");
		return messageDto;
	}

	@Override
	public List<MessageDto> getAllMessages() {
		List<Message> messageEntities = messageRepository.findAll();
		List<MessageDto> messageDtos = new ArrayList<>(messageEntities.size());

		for (Message entity : messageEntities) {
			MessageDto dto = new MessageDto();
			dto.content = entity.getContent();
			dto.createdTime = entity.getCreatedAt();

			Account recipientAccount = entity.getRecipientAccount();
			if (recipientAccount != null) {
				dto.recipientUsername = recipientAccount.getUsername();
			}

			Account senderAccount = entity.getSenderAccount();
			if (senderAccount != null) {
				dto.senderUsername = senderAccount.getUsername();
			}

			messageDtos.add(dto);
		}

		return messageDtos;
	}

	@Override
	public void handleEvent(EventInfo eventInfo) {
		if (eventInfo.event == ChatterEvent.LOGGED_IN_ACCOUNT) {
			String username = (String) eventInfo.getVariable(Variable.USERNAME);
			loggedInAccount = accountRepository.findByUsername(username);
		}
	}

	@Override
	public void receivedPost(Post post) {
		if (post instanceof MessageDto messageDto) {
			Account senderAccount = accountRepository.findByUsername(messageDto.senderUsername);
			Account recipientAccount = accountRepository.findByUsername(messageDto.recipientUsername);
			Message entity = new Message();
			entity.setSenderAccount(senderAccount);
			entity.setRecipientAccount(recipientAccount);
			entity.setContent(messageDto.content);
			messageRepository.saveAndFlush(entity);

			EventInfo event = new EventInfo(ChatterEvent.INCOMING_MESSAGE);
			event.putVariable(Variable.MESSAGE, messageDto);
			eventService.sendEvent(event);
		}

	}

}
