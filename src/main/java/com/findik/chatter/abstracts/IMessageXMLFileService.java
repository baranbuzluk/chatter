package com.findik.chatter.abstracts;

import java.io.File;

import com.findik.chatter.entity.Message;

public interface IMessageXMLFileService {

	void writeToXml(Message message);

	Message readFromXml(File xml);

}
