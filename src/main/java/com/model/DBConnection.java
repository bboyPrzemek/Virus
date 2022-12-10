package com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static String url = "jdbc:postgresql://localhost:5432/postgres";
	private static String userName = "postgres";
	private static String password = "123";

	public static Connection getConnection() {
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
