package com.findik.chatter.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.findik.chatter.entity.MessageEntity;

public interface IServices {

	void createMessage(MessageEntity message);

	ArrayList<MessageEntity> getMessage(MessageEntity message) throws SQLException;

	void updateMessage(MessageEntity message);

	void deleteMessage(MessageEntity message);
}
