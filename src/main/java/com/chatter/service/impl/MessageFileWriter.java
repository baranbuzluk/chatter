package com.chatter.service.impl;

import java.io.File;

import com.chatter.dto.MessageDto;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

enum MessageFileWriter {

	XML(new XmlMapper(), ".xml"), JSON(new JsonMapper(), ".json");

	private ObjectMapper mapper;

	private String fileExtension;

	private MessageFileWriter(ObjectMapper mapper, String fileExtension) {
		this.mapper = mapper;
		this.fileExtension = fileExtension;
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.configure(Feature.IGNORE_UNKNOWN, true);
	}

	private static File getMessageDirectory() {
		String userHomePath = System.getProperty("user.home");
		File dstFile = new File(userHomePath, "CHATTER_MESSAGES");
		if (!dstFile.exists()) {
			dstFile.mkdir();
		}
		return dstFile;
	}

	public void write(MessageDto obj) {
		if (obj != null) {
			try {
				File dstFile = getMessageDirectory();
				StringBuilder fileName = new StringBuilder();
				fileName.append("Message-");
				fileName.append(System.nanoTime());
				fileName.append(this.fileExtension);
				File messageFile = new File(dstFile, fileName.toString());
				this.mapper.writerFor(MessageDto.class).writeValue(messageFile, obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
