package com.chatter.core.mapper;

import java.io.File;

import org.springframework.stereotype.Component;

import com.chatter.core.abstracts.FileMapper;
import com.chatter.core.entity.Message;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class XmlFileMapperForMessage implements FileMapper<Message> {

	private XmlMapper xmlMapper;

	public XmlFileMapperForMessage() {
		xmlMapper = new XmlMapper();
		xmlMapper.registerModule(new JavaTimeModule());
		xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
	}

	@Override
	public boolean writeToFile(Message message, File file) {
		try {
			if (message != null && file != null) {
				xmlMapper.writerFor(Message.class).writeValue(file, message);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Message readFile(File file) {
		try {
			if (validateFileToRead(file)) {
				ObjectReader readerFor = xmlMapper.readerFor(Message.class);
				return readerFor.readValue(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean validateFileToRead(File file) {
		return file != null && file.exists() && file.isFile() && file.canRead();
	}

}
