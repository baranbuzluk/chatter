package com.findik.chatter.service;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.findik.chatter.database.ConnectDB;
import com.findik.chatter.entity.MessageEntity;

public class IMessageService implements IServices {

	@Override
	public void createMessage(MessageEntity message) {

		PreparedStatement statement = null;
		try {
			ConnectDB.getConnection();
			String sql = "insert into message (messageID, username, content) values(?, ?, ?)";
			statement = ConnectDB.getConnection().prepareStatement(sql);
			BigDecimal decimalMessageID = new BigDecimal(message.getMessageID());
			statement.setBigDecimal(1, decimalMessageID);
			statement.setString(2, message.getUsername());
			statement.setString(3, message.getContent());
			statement.executeUpdate();

		} catch (SQLException exception) {
			System.err.println(ConnectDB.getConnection());
		} finally {
			try {
				statement.close();
				ConnectDB.closeConnection();
			} catch (SQLException ex) {
				System.err.println(ConnectDB.getConnection());
			}
		}

	}

	@Override
	public ArrayList<MessageEntity> getMessage(MessageEntity message) throws SQLException {

		Statement statement = null;
		ResultSet resultSet;
		ArrayList<MessageEntity> messages = null;

		try {
			statement = ConnectDB.getConnection().createStatement();
			resultSet = statement.executeQuery("Select * from message");
			messages = new ArrayList<MessageEntity>();

			while (resultSet.next()) {
				messages.add(new MessageEntity((resultSet.getBigDecimal("messageID")).toBigInteger(),
						resultSet.getString("username"), resultSet.getString("content")));
			}
		} catch (SQLException exception) {
			System.err.println(ConnectDB.getConnection());
		} finally {
			statement.close();
			ConnectDB.closeConnection();
		}
		return messages;

	}

	@Override
	public void updateMessage(MessageEntity message) {

		PreparedStatement statement = null;
		try {
			ConnectDB.getConnection();
			String sql = "UPDATE message SET messageID='" + message.getMessageID() + "',username='"
					+ message.getUsername() + "',content=" + message.getContent();

			statement = ConnectDB.getConnection().prepareStatement(sql);
			statement.executeUpdate();

		} catch (SQLException exception) {
			System.err.println(ConnectDB.getConnection());
		} finally {
			try {
				statement.close();
				ConnectDB.closeConnection();
			} catch (SQLException ex) {
				System.err.println(ConnectDB.getConnection());
			}

		}
	}

	@Override
	public void deleteMessage(MessageEntity message) {

		PreparedStatement statement = null;
		try {
			ConnectDB.getConnection();
			String sql = "DELETE FROM product WHERE messageID=" + message.getMessageID();
			statement = ConnectDB.getConnection().prepareStatement(sql);
			statement.executeUpdate();

		} catch (SQLException exception) {
			System.err.println(ConnectDB.getConnection());
		} finally {
			try {
				statement.close();
				ConnectDB.closeConnection();
			} catch (SQLException ex) {
				System.err.println(ConnectDB.getConnection());
			}

		}
	}

}
