package com.chatter.core.mapper;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chatter.core.abstracts.MessageWriter;
import com.chatter.core.entity.Message;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class XmlFileMessageWriter implements MessageWriter<File> {

	private static final String SEPARATOR = "_";

	private XmlMapper xmlMapper;

	private File messageFolder;

	@Autowired
	public XmlFileMessageWriter(File messageFolder) {
		this.messageFolder = messageFolder;
		xmlMapper = new XmlMapper();
		xmlMapper.registerModule(new JavaTimeModule());
		xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
	}

	@Override
	public File write(Message message) {

		try {
			boolean checkFile = messageFolder != null && messageFolder.exists() && messageFolder.isDirectory();
			if (message != null && checkFile) {
				String fileName = generateFileName(message);
				File messageFile = new File(messageFolder, fileName);
				xmlMapper.writerFor(Message.class).writeValue(messageFile, message);
				return messageFile;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String generateFileName(Message message) {
		Integer id = message.getId();
		LocalDateTime createdAt = message.getCreatedAt();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
		String formattedCreatedAt = dateTimeFormatter.format(createdAt);

		StringBuilder fileNameBuilder = new StringBuilder();
		fileNameBuilder.append("Message");
		fileNameBuilder.append(SEPARATOR);
		fileNameBuilder.append(id);
		fileNameBuilder.append(SEPARATOR);
		fileNameBuilder.append(formattedCreatedAt);
		fileNameBuilder.append(".xml");
		return fileNameBuilder.toString();
	}

}
