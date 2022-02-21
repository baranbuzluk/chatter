package com.findik.chatter.main;

import com.findik.chatter.database.ConnectDB;

public class MainApplication {

	public static void main(String[] args) {
		System.err.println(ConnectDB.getConnection());
	}
}
