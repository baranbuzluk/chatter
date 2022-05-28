package com.chatter.xml;

import java.io.File;
import java.io.InputStream;

import com.chatter.entity.Message;

public interface MessageXMLFileService {

	void writeToXml(Message message);

	Message readFromXml(File xml);

	Message readFromXml(InputStream inputStream);

}
