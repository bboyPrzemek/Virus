package com.model;
public class User {
	private String userID;
	private String username;
	
	public User(String userID, String username) {
		this.userID = userID;
		this.username = username;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUserID() {
		return this.userID;
	}
	
	public String getUsername() {
		return this.username;
	}
}
