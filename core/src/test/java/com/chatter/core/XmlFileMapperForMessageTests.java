package com.chatter.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.chatter.core.abstracts.FileMapper;
import com.chatter.core.entity.Message;
import com.chatter.core.mapper.XmlFileMapperForMessage;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

class XmlFileMapperForMessageTests {

	static FileMapper<Message> fileMapperForMessage;

	File file;

	static XmlMapper xmlMapper;

	@BeforeAll
	static void setUpAll() {
		xmlMapper = new XmlMapper();
		xmlMapper.registerModule(new JavaTimeModule());
		xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
		fileMapperForMessage = new XmlFileMapperForMessage();
	}

	@AfterEach
	void tearDown() throws Exception {
		if (file != null) {
			file.delete();
			file = null;
		}
	}

	@AfterAll
	static void tearDownAll() {
		fileMapperForMessage = null;
		xmlMapper = null;
	}

	@Test
	void When_MessageAndFileNotNull_Expect_isMessageToBeWrittenToFileAndResultAsTrue() throws Exception {
		Message actualMessage = new Message("user", "Hello Word");
		file = File.createTempFile("test", ".xml");
		boolean writeToFile = fileMapperForMessage.writeToFile(actualMessage, file);
		Message expectedMessage = xmlMapper.readValue(file, Message.class);

		assertTrue(writeToFile);
		assertEquals(expectedMessage.getUsername(), actualMessage.getUsername());
		assertEquals(expectedMessage.getContent(), actualMessage.getContent());
		assertNotNull(expectedMessage.getCreatedAt());
		assertNull(expectedMessage.getId());
	}

	@Test
	void When_MessageIsNullAndFileNotNull_Expect_isResultAsFalse() throws Exception {
		Message actualMessage = null;
		file = File.createTempFile("test", ".xml");
		boolean expectedValueToWrite = fileMapperForMessage.writeToFile(actualMessage, file);
		assertFalse(expectedValueToWrite);
	}

	@Test
	void When_MessageNotNullAndFileIsNull_Expect_isResultAsFalse() throws Exception {
		Message actualMessage = new Message("test", "message");
		file = null;
		boolean expectedValueToWrite = fileMapperForMessage.writeToFile(actualMessage, file);
		assertFalse(expectedValueToWrite);
	}

	@Test
	void When_XmlFileExists_Expect_isMessage() throws Exception {
		String actualUsername = "username";
		String actualContent = "Hello Word";
		LocalDateTime actualCreatedAt = LocalDateTime.now();
		StringBuilder xmlAsString = new StringBuilder();
		xmlAsString.append("<?xml version='1.0' encoding='UTF-8'?>");
		xmlAsString.append("<Message>");
		xmlAsString.append("<id/>");
		xmlAsString.append("<username>" + actualUsername + "</username>");
		xmlAsString.append("<content>" + actualContent + "</content>");
		xmlAsString.append("<createdAt>" + actualCreatedAt + "</createdAt>");
		xmlAsString.append("<createdAt>" + actualCreatedAt + "</createdAt>");
		xmlAsString.append("</Message>");

		file = File.createTempFile("test", ".xml");
		PrintStream printStream = new PrintStream(file, Charset.forName("utf-8"));
		printStream.print(xmlAsString);
		printStream.close();

		Message expectedMessage = fileMapperForMessage.readFile(file);

		assertEquals(expectedMessage.getContent(), actualContent);
		assertEquals(expectedMessage.getUsername(), actualUsername);
		assertEquals(expectedMessage.getCreatedAt(), actualCreatedAt);
		assertNull(expectedMessage.getId());
	}

	@Test
	void When_XmlFileNullOrNotExisting_Expect_isMessageIsNull() {
		file = new File("test123456.test123456");
		if (file.exists()) {
			file.delete();
		}
		Message message = fileMapperForMessage.readFile(file);
		assertNull(message);

		file = null;
		message = fileMapperForMessage.readFile(file);
		assertNull(message);
	}

}
