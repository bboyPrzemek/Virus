package com.model;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDeck {
	private List<Card> playerDeck; 
	
	public PlayerDeck() {
		this.playerDeck = new ArrayList<>();	
		
	}
	
	public void addCard(Card card) {
		playerDeck.add(card);
	}
	
	
	
	public List<Card> removeCards() {
		return null;
	}
	
	
	
	

}
