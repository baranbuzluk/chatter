package com.chatter.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import com.chatter.connection.CommunicationManager;
import com.chatter.data.entity.Account;
import com.chatter.data.entity.Message;
import com.chatter.data.repository.AccountRepository;
import com.chatter.data.repository.MessageRepository;
import com.chatter.event.ChatterEvent;
import com.chatter.event.ChatterEventListener;
import com.chatter.event.ChatterEventProperties;
import com.chatter.event.EventInfo;
import com.chatter.event.EventManager;

import javafx.scene.Parent;

@Component
public class CommonServiceImpl implements CommonService {

	private final EventManager eventManager;

	private final MainViewService mainViewService;

	private final AccountRepository accountRepository;

	private final MessageRepository messageRepository;

	private final CommunicationManager communicationManager;

	@Autowired
	public CommonServiceImpl(EventManager eventManager, MainViewService mainViewService,
			AccountRepository accountRepository, MessageRepository messageRepository,
			CommunicationManager communicationManager) {
		this.eventManager = eventManager;
		this.mainViewService = mainViewService;
		this.accountRepository = accountRepository;
		this.messageRepository = messageRepository;
		this.communicationManager = communicationManager;
	}

	@Override
	public void sendEvent(EventInfo event) {
		eventManager.sendEvent(event);
	}

	@Override
	public void showInMainWindow(Parent rootPane) {
		mainViewService.show(rootPane);
	}

	@Override
	public Account getAccountByUsernameAndPassword(String username, String password) {
		if (username == null || password == null) {
			return null;
		} else {
			byte[] bytesOfText = password.getBytes();
			String passwordMd5 = DigestUtils.md5DigestAsHex(bytesOfText);
			return accountRepository.findByUsernameAndPassword(username, passwordMd5);
		}
	}

	@Override
	public boolean existsByUsername(String username) {
		if (username == null)
			throw new IllegalArgumentException("username must not null!");
		return accountRepository.existsByUsername(username);
	}

	@Override
	public Account saveAccount(Account account) {
		return accountRepository.save(account);
	}

	@Override
	public List<Message> getAllMessagesFromDatabase() {
		return messageRepository.findAllByOrderByCreatedAtAsc();
	}

	@Override
	public boolean sendMessage(Message message) {
		messageRepository.save(message);
		communicationManager.sendMessage(message);
		EventInfo eventInfo = new EventInfo(ChatterEvent.OUTGOING_MESSAGE);
		eventInfo.put(ChatterEventProperties.MESSAGE, message);
		sendEvent(eventInfo);
		return true;
	}

	@Override
	public void registerEventListener(ChatterEventListener chatterEventListener) {
		eventManager.registerListener(chatterEventListener);
	}

	@Override
	public List<Message> findMessageByrecipientHostAddressAndSenderHostAdress(String recipientHostAddress,
			String senderHostAddress) {
		if (recipientHostAddress != null && senderHostAddress != null) {
			List<Message> messages = this.messageRepository
					.findMessageByrecipientHostAddressAndSenderHostAdress(recipientHostAddress, senderHostAddress);
			if (messages != null) {
				return messages;
			}
		}
		return Collections.emptyList();
	}

}
