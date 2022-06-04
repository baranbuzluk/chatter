package com.chatter.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.chatter.core.util.XmlUtils;
import com.chatter.data.entity.Message;

public class ServerApp {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(9999);
		Socket accept = serverSocket.accept();
		File file = new File("C:/CHATTER/Gelen.xml");
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		int read = 0;
		while ((read = accept.getInputStream().read()) != -1) {
			System.err.println(read + " received from client..");
			fileOutputStream.write(read);
		}
		serverSocket.close();
		fileOutputStream.close();
		Message readXmlFile = XmlUtils.readXmlFile(file, Message.class);
		System.err.println(readXmlFile);

	}
}
