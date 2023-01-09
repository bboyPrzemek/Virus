package com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static String url = System.getenv("DB_HOST");
	private static String userName = System.getenv("DB_USERNAME");
	private static String password = System.getenv("DB_PASSWORD");

	public static Connection getConnection() {
		System.out.println(url);
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, userName, password);
			System.out.println("Connection Established successfully");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return con;
	}
}
