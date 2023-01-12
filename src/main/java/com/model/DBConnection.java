package com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private static String url = "jdbc:postgresql://database-1.ciomebztgfmj.eu-central-1.rds.amazonaws.com:5432/postgres";
	private static String userName = "postgres";
	private static String password = "12345678";

	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(url, userName, password);
			System.out.println("Connection Established successfully");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
		
		return con;
	}
}
