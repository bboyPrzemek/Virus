package com.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDeck {
	List<CardZone> cardZones = new ArrayList<>();;
	
	

	public PlayerDeck() {
		this.cardZones.add(new CardZone("heart"));
		this.cardZones.add(new CardZone("maw"));
		this.cardZones.add(new CardZone("bone"));
		this.cardZones.add(new CardZone("brain"));

	}

	public PlayerDeck(PlayerDeck other) {
		this.cardZones.add(new CardZone("heart"));
		this.cardZones.add(new CardZone("maw"));
		this.cardZones.add(new CardZone("bone"));
		this.cardZones.add(new CardZone("brain"));
		
		

		for (Card c : other.getCardZoneByType("heart").getCards()) {
			this.getCardZoneByType("heart").addCard(c);
		}
		for (Card c : other.getCardZoneByType("bone").getCards()) {
			this.getCardZoneByType("bone").addCard(c);
		}
		for (Card c : other.getCardZoneByType("maw").getCards()) {
			this.getCardZoneByType("maw").addCard(c);
		}
		for (Card c : other.getCardZoneByType("brain").getCards()) {
			this.getCardZoneByType("brain").addCard(c);
		}
	}
	
	public CardZone getCardZoneByType(String type) {
		CardZone cZone = null;
		for (CardZone cardZone : this.cardZones) {
			if (cardZone.getType().equals(type)) {
				cZone = cardZone;
			}
			
		}
		return cZone;
	}
	

	// walidacje trzeba zrobic jeszcze czy wgl mozna dodac taka karte
	public Boolean addCard(Card card) {

		Boolean cardAdded = false;
		if (card.getOrganType().name() == "HEART" || card.getTemporaryOrganType().name() == "HEART") {
			cardAdded = this.getCardZoneByType("heart").addCard(card);
		} else if (card.getOrganType().name() == "MAW" || card.getTemporaryOrganType().name() == "MAW") {
			cardAdded = this.getCardZoneByType("maw").addCard(card);
		} else if (card.getOrganType().name() == "BONE" || card.getTemporaryOrganType().name() == "BONE") {
			cardAdded = this.getCardZoneByType("bone").addCard(card);
		} else if (card.getOrganType().name() == "BRAIN" || card.getTemporaryOrganType().name() == "BRAIN") {
			cardAdded = this.getCardZoneByType("brain").addCard(card);
		}
		return cardAdded;
	}

	

}
