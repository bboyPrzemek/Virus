package com.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.model.DBConnection;

@Path("/game")
public class GameService {
	 
	@Path("/new/{numOfPlayers}")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public static String createNewGame(@PathParam("numOfPlayers") int numOfPlayers) {

    		Connection connection = DBConnection.getConnection();
    		String gameId = null;
    		try {
    			gameId = UUID.randomUUID().toString();
    			PreparedStatement st = connection.prepareStatement("INSERT INTO game (gameid, numofplayers) VALUES (?,?)");
    			st.setString(1, gameId);
    			st.setInt(2, numOfPlayers);
    			st.executeUpdate();
    			st.close();
    			System.out.println("New Game: ID " + gameId + " has been created");
    		} catch (Exception e) {
    			System.out.print(e.getMessage());
    		} finally {
    			try {
    				connection.close();
    			} catch (Exception e) {
    				// TODO: handle exception
    			}
    		}
			return gameId;
       
    }
    
	@Path("/search/{id}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public static int searchGame(@PathParam("id") String gameid) {

    		Connection connection = DBConnection.getConnection();
    		int count = 0;
    		try {
    			Statement stmt = connection.createStatement();
    		    ResultSet rs = stmt.executeQuery("SELECT count(*) as total from game where gameid = " + "'" + gameid + "'");
    		    rs.next();
    		    count = rs.getInt("total");
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
    		return count;
			
       
    }
    
  
}
