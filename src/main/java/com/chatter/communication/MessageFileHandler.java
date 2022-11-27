package com.chatter.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.chatter.dto.MessageDto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

enum MessageFileHandler implements IOStreamHandler<MessageDto> {

	XML(new XmlMapper(), ".xml"), JSON(new JsonMapper(), ".json");

	private ObjectMapper mapper;

	private String fileExtension;

	private MessageFileHandler(ObjectMapper mapper, String fileExtension) {
		this.mapper = mapper;
		this.fileExtension = fileExtension;
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.configure(Feature.IGNORE_UNKNOWN, true);
	}

//	private static File getMessageDirectory() {
//		String userHomePath = System.getProperty("user.home");
//		File dstFile = new File(userHomePath, "CHATTER_MESSAGES");
//		if (!dstFile.exists()) {
//			dstFile.mkdir();
//		}
//		return dstFile;
//	}

	@Override
	public MessageDto read(InputStream stream) {
		try (JsonParser parser = mapper.createParser(stream)) {
			return parser.readValueAs(MessageDto.class);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("olmadı kardes");
		}
		return null;
	}

	@Override
	public void write(MessageDto obj, OutputStream outputStream) {
		try (JsonGenerator generator = mapper.createGenerator(outputStream)) {
			generator.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
