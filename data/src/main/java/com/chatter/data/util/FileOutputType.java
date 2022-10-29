package com.chatter.data.util;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public enum FileOutputType {
	XML(XmlMapper.class, ".xml"), JSON(JsonMapper.class, ".json");

	private ObjectMapper mapper;

	private String fileExtension;

	private <T extends ObjectMapper> FileOutputType(Class<T> clazz, String fileExtension) {
		try {
			this.fileExtension = fileExtension;
			mapper = clazz.getDeclaredConstructor().newInstance();
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			mapper.configure(Feature.IGNORE_UNKNOWN, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ObjectMapper getObjectMapper() {
		return mapper;
	}

	public String getFileExtension() {
		return fileExtension;
	}
}
