package com.chatter.util;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class XmlUtils {

	private XmlUtils() {
	}

	public static <T> T readXmlFile(File file, Class<T> clazz) {
		File f = Objects.requireNonNull(file, "file can not be null!");
		if (!f.exists() || !f.isFile()) {
			return null;
		}
		try {
			XmlMapper xmlMapper = createXmlMapper();
			return xmlMapper.readerFor(clazz).readValue(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> void writeObjectToFile(T object, File file) {
		File f = Objects.requireNonNull(file, "file can not be null!");
		T o = Objects.requireNonNull(object, "object can not be null!");
		try {
			XmlMapper xmlMapper = createXmlMapper();
			xmlMapper.writeValue(f, o);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static XmlMapper createXmlMapper() {
		XmlMapper xmlMapper = new XmlMapper();
		xmlMapper.registerModule(new JavaTimeModule());
		xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
		return xmlMapper;
	}

}
