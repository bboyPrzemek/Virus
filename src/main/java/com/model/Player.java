package com.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.websocket.Session;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Player {
	private Hand hand;
	private PlayerDeck playerDeck;
	private String name;
	private Boolean playable = false;
	private String clientID;
	@JsonIgnore
	private Session session;
	
	

	public Player() {
		this.hand = new Hand();
		this.playerDeck = new PlayerDeck();
	}
	public Player(Hand hand) {
		this.hand = hand;
		this.playerDeck = new PlayerDeck();
	}
	
	
	public Player(Session session, String name) {
		this.session = session;
		this.name = name;
		this.playerDeck = new PlayerDeck();
		this.hand = new Hand();
	}

	public Player(Session session,String name, String clientID) {
		this.session = session;
		this.name = name;
		this.clientID = clientID;
		this.hand = new Hand();
	}

	public Card playCard(int index) {
		return this.hand.removeCard(index);
	}

	//dobierz karty
	public void drawCards(List<Card> cards) {
		this.hand.addCardsToHand(cards);
	}

	public void updatePlayerDeck(Card card) {
		this.playerDeck.addCard(card);
	}

	public List<Card> removeCards(List<Integer> indexes) {
		Collections.reverse(indexes);
		List<Card> tempCardList = new ArrayList<>();
		for (Integer i : indexes) {
			tempCardList.add(this.hand.removeCard(i));
		}
		return tempCardList;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(session);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return  Objects.equals(session, other.session);
	}
	
	
	
	

	

	
}
