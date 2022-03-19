package com.findik.chatter.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectDB {

	private final static String userName = "postgres";

	private final static String password = "postgres";

	private final static String dbUrl = "jdbc:postgresql://localhost:5432/CHATTER";

	private static Connection connection = null;

	private static ConnectDB connectDB = null;

	private ConnectDB() {
	}

	public static ConnectDB getConnection() {
		if (connection == null) {
			try {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(dbUrl, userName, password);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return connectDB;

	}

	public PreparedStatement executeSqlCommand(String sql) {
		try {
			return connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void close() {
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
