package com.stefanski.amazonbooksearcher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLtest {

	public static void main(String[] args) {

		createDB();

	}

	public static void createDB() {

		String databaseName = "dbName";
		String userName = "root";
		String password = "root";
		String path = System.getProperty("user.dir");

		try {

			String url = "jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=convertToNull";
			Connection connection = DriverManager.getConnection(url, userName, password);

			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery("SHOW DATABASES LIKE 'dbName'");
			rs.next();
			// boolean exists = rs.getInt("SELECT COUNT(*)") > 0;
			System.out.println(rs.getInt("SELECT COUNT(*)"));
			boolean exists = true;

			if (!exists) {

				System.out.println("No db. Create new db.");
			}
			// String sql = "CREATE DATABASE " + databaseName;
			// statement.executeUpdate(sql);

			statement.close();
			// JOptionPane.showMessageDialog(null, databaseName + " Database has been
			// created successfully",
			// "System Message", JOptionPane.INFORMATION_MESSAGE);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
