package com.findik.chatter.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {

	private static Connection connection = null;

	static {

		String userName = "postgres";
		String password = "postgres";
		String dbUrl = "jdbc:postgresql://localhost:5432/CHATTER";

		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(dbUrl, userName, password);
		}

		catch (ClassNotFoundException | SQLException exception) {
			System.out.println("Error: " + exception.getMessage());
			System.out.println("Error Code: " + ((SQLException) exception).getErrorCode());
		}
	}

	public static Connection getConnection() {
		return connection;
	}
}
