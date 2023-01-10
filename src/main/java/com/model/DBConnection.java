package com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static String url = "jdbc:postgresql://postgres.cx6jnlua8siv.us-east-1.rds.amazonaws.com:5432/postgres";
	private static String userName = "postgres";
	private static String password = "12345678";

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
