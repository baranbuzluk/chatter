package com.findik.chatter.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.findik.chatter.core.ConfigPropertiesService;
import com.findik.chatter.entity.Message;
import com.thoughtworks.xstream.XStream;

@Component
public class MessageXMLFileServiceImpl implements MessageXMLFileService {

	private static final String XML_EXTENSION = ".xml";

	private static final String MESSAGE_NAME = "Message";

	private static final String SEPARATOR = "_";

	private ConfigPropertiesService applicationSettingService;

	private XStream xStream = null;

	@Autowired
	public MessageXMLFileServiceImpl(ConfigPropertiesService applicationSettingService) {
		this.applicationSettingService = applicationSettingService;
		xStream = createXstream();
	}

	@Override
	public void writeToXml(Message message) {
		String xmlFilename = createMessageXmlFileName();
		File xmlFile = createXMLFileForMessage(xmlFilename);
		try (FileOutputStream fos = new FileOutputStream(xmlFile)) {
			xStream.toXML(message, fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Message readFromXml(File xml) {
		return (Message) xStream.fromXML(xml);
	}

	private String createMessageXmlFileName() {
		LocalDateTime now = LocalDateTime.now();
		int datetimeNano = now.getNano();
		int datetimeSecond = now.getSecond();
		StringBuilder fileNameBuilder = new StringBuilder();
		fileNameBuilder.append(MESSAGE_NAME);
		fileNameBuilder.append(SEPARATOR);
		fileNameBuilder.append(datetimeSecond);
		fileNameBuilder.append(datetimeNano);
		return fileNameBuilder.toString();
	}

	private File createXMLFileForMessage(String filename) {
		Path messageOutputDirectory = applicationSettingService.getMessageOutputDirectory();
		String fileWithExtension = filename.concat(XML_EXTENSION);
		Path messageFilePath = messageOutputDirectory.resolve(fileWithExtension);
		return messageFilePath.toFile();
	}

	private XStream createXstream() {
		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(Message.class);
		return xstream;
	}

}
