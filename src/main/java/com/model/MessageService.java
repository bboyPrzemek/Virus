package com.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

enum State{
	
	ERROR("0"),
	CONNECT("1"),
	START("2"),
	UPDATE("3"),
	CLOSE("4");
	
	
	private final String stateId;
	
	private State(String stateId) {
		this.stateId = stateId;
	}
	
	public String getStateId() {
		return this.stateId ;
	}
	
}

public class MessageService {
	
	public class MessageWrapper {
		private State state;
		private Game game;
		private Hand hand;
		
		public MessageWrapper(State state, Game game, Hand hand) {
			this.state = state;
			this.game = game;	
			this.hand = hand;
		}
		
		public MessageWrapper(State state, Game game) {
			this.state = state;
			this.game = game;	
		}
		
		public State getState() {
			return this.state;
		}
		
		public Game getGame() {
			return this.game;
		}
		
		public Hand getHand() {
			return this.hand;
		}
		
	}
	
	public String message(MessageWrapper messageWrapper) {
		System.out.println(messageWrapper);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = "";
		try {
			json = ow.writeValueAsString(messageWrapper);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(json);
		return json;
	}
	
}
