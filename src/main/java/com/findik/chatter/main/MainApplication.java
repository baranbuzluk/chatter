package com.findik.chatter.main;

import com.findik.chatter.database.ConnectDB;

public class MainApplication {

	public static void main(String[] args) {
		System.out.println("Hello Word");
		System.out.println("Hello Word");

		ConnectDB dbObject = new ConnectDB();
		dbObject.getConnection();
	}
}
