package com.findik.chatter.xml;

import java.io.File;

import com.findik.chatter.entity.Message;

public interface MessageXMLFileService {

	void writeToXml(Message message);

	Message readFromXml(File xml);

}
