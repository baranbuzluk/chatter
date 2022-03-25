package com.findik.chatter.xml;

import java.io.File;
import java.io.IOException;

import com.findik.chatter.entity.Message;
import com.thoughtworks.xstream.XStream;

public class MessageXMLConverter {

	XStream xstream = null;

	public void writeToXml(Message message) {

		File xmlFile = new File("C:\\Chatter\\messages.xml");
		if (!xmlFile.exists()) {
			try {
				xmlFile.createNewFile();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

		xstream = new XStream();
		xstream.alias("Message", Message.class);
		xstream.toXML(message);
	}

	public Message readFromXml(File xml) {
		Message messageFromXml = (Message) xstream.fromXML(xml);
		return messageFromXml;
	}
}
