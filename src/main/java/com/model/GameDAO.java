package com.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class GameDAO {

	public Game getGameById(String gameId) {
		Connection connection = DBConnection.getConnection();
		Game game = null;
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT gameid, numofplayers from game where gameid = " + "'" + gameId + "'");

			if (rs.next()) {
				String gameid = rs.getString("gameid");
				int numofplayers = rs.getInt("numofplayers");
				System.out.println(gameid + " " + numofplayers);
				game = new Game(gameid, numofplayers);
				System.out.println(rs);
			}
			stmt.close();

		} catch (Exception e) {
			System.out.print(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return game;

	}

}
