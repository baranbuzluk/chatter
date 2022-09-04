package com.chatter.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.chatter.core.abstracts.MessageWriter;
import com.chatter.core.entity.Account;
import com.chatter.core.entity.Message;
import com.chatter.core.mapper.XmlFileMessageWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

class XmlFileMessageWriterTests {

	static MessageWriter<File> messageWriter;

	static XmlMapper xmlMapper;

	@BeforeAll
	static void setUpAll() {
		xmlMapper = new XmlMapper();
		xmlMapper.registerModule(new JavaTimeModule());
		xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
		String tempFolder = System.getProperty("java.io.tmpdir");
		File messageFolder = new File(tempFolder);
		messageWriter = new XmlFileMessageWriter(messageFolder);
	}

	@AfterAll
	static void tearDownAll() {
		messageWriter = null;
		xmlMapper = null;
	}

	@Test
	void When_MessageNotNull_Expect_isToWriteFile() throws Exception {
		Account account = new Account("testUsername", "testPassword");
		Message actualMessage = new Message(account, "Hello Word");
		File file = messageWriter.write(actualMessage);
		assertNotNull(file);

		Message expectedMessage = xmlMapper.readValue(file, Message.class);
		assertEquals(expectedMessage.getUsername(), actualMessage.getUsername());
		assertEquals(expectedMessage.getContent(), actualMessage.getContent());
		assertNotNull(expectedMessage.getCreatedAt());
		assertNull(expectedMessage.getId());
	}

	@Test
	void When_MessageIsNull_Expect_isToReturnNull() throws Exception {
		Message actualMessage = null;
		File file = messageWriter.write(actualMessage);
		assertNull(file);
	}

}
