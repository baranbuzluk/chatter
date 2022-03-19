package com.findik.chatter.service;

import java.math.BigInteger;
import java.util.ArrayList;

import com.findik.chatter.entity.Message;

public interface IMessageDatabaseOperation {

	void create(Message message);

	ArrayList<Message> getAll();

	void update(Message message);

	void delete(BigInteger id);
}
