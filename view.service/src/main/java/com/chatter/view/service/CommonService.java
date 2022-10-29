package com.chatter.view.service;

import java.util.List;

import com.chatter.data.entity.Account;
import com.chatter.data.entity.Message;
import com.chatter.event.data.EventInfo;

import javafx.scene.Parent;

public interface CommonService {

	Account getAccountByUsernameAndPassword(String username, String password);

	void showInMainWindow(Parent rootPane);

	void sendEvent(EventInfo event);

	boolean existsByUsername(String username);

	Account saveAccount(Account account);

	List<Message> getAllMessagesFromDatabase();

	Message saveMessage(Message message);

}
