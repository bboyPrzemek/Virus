package com.model;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDAO {
	public void saveUser(User user) {

		Connection connection = DBConnection.getConnection();

		try {
			PreparedStatement st = connection.prepareStatement("INSERT INTO users (UserID, Username) VALUES (?,?)");

			st.setString(1, user.getUserID());
			st.setString(2, user.getUsername());
			st.executeUpdate();

			st.close();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

}
