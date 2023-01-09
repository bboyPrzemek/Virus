package com.model;

import java.util.ArrayList;
import java.util.Arrays;
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
	private Boolean isPlayable = false;
	private int clientID;
	private Session session;
	private int posX;
	private int posY;

	public Player() {
		this.hand = new Hand();
		this.playerDeck = new PlayerDeck();
	}

	public Player(Hand hand) {
		this.hand = hand;
		this.playerDeck = new PlayerDeck();
	}

	public void setIsPlayable() {
		this.isPlayable = true;
	}

	public Boolean getIsPlayable() {
		return this.isPlayable;
	}

	public void setIsNonPlayable() {
		this.isPlayable = false;
	}

	public Player(Session session, String name) {
		this.session = session;
		this.name = name;
		this.playerDeck = new PlayerDeck();
		this.hand = new Hand();
	}

	public Player(Session session, String name, int clientID) {
		this.session = session;
		this.name = name;
		this.clientID = clientID;
		this.hand = new Hand();
	}

	public Card playCard(int index) {
		return this.hand.removeCard(index);
	}

	public void playCard(Card card) {
		this.hand.removeCard(card);
	}

	public Card getCardByIndex(int index) {
		return this.hand.getCardByIndex(index);
	}

	// dobierz karty
	public void drawCards(List<Card> cards) {
		this.hand.addCardsToHand(cards);
	}

	public Boolean updatePlayerDeck(Card card) {
		return this.playerDeck.addCard(card);
	}

	public List<Card> removeAllCards() {
		return removeCards(new int[] { 0, 1, 2 });
	}

	public List<Card> removeCards(int[] indexes) {
		Arrays.sort(indexes);
		List<Card> tempCardList = new ArrayList<>();

		for (int i = indexes.length - 1; i >= 0; i--) {
			tempCardList.add(this.hand.removeCard(indexes[i]));
		}
		return tempCardList;
	}

	public List<Card> organizePlayerDeck() {
		List<Card> cards = new ArrayList<>();

		for (CardZone cardZone : this.playerDeck.getCardZones()) {
			if (cardZone.countVirusCardsNumber() == 2) {
				cards.addAll(cardZone.destroyZone());
			} else if (cardZone.countVirusCardsNumber() == 1 && cardZone.countRemedyCardsNumber() == 1) {
				cards.addAll(cardZone.removeVirusAndRemedyFromBone());
			}
		}
		return cards;
	}

	public Boolean moveZoneToOtherPlayer(Player player, String zone) {

		Boolean cardsAdded = false;

		System.out.println(player.getCardZoneByType(zone).isEmpty());
		
		System.out.println(!this.getCardZoneByType(zone).isEmpty());
		System.out.println(!this.getCardZoneByType(zone).isImmuned());
		System.out.println(player.getName());
		
		System.out.println(this.name);
		if (player.getCardZoneByType(zone).isEmpty() && !this.getCardZoneByType(zone).isEmpty() && !this.getCardZoneByType(zone).isImmuned()) {
			System.out.println("jestem tu");
			
			for (Card cards : this.getCardZoneByType(zone).getCards()) {
				player.updatePlayerDeck(cards);
			}
			this.getCardZoneByType(zone).clearZone();
			cardsAdded = true;
		}
		return cardsAdded;
	}
	
	public Boolean switchOrganWithOtherPlayer(Player player, String zone) {
		Boolean hasSwitched = false;
		CardZone thisZone = this.getPlayerDeck().getCardZoneByType(zone);
		CardZone otherPlayerZone = player.getPlayerDeck().getCardZoneByType(zone);
		
		if ((!thisZone.isEmpty() && !thisZone.isImmuned()) && (!otherPlayerZone.isEmpty() && !otherPlayerZone.isImmuned())) {
			CardZone thisZoneCopy = new CardZone(thisZone);
			CardZone otherZoneCopy = new CardZone(otherPlayerZone);
			
			thisZone = otherZoneCopy;
			otherPlayerZone = thisZoneCopy;
			
			hasSwitched = true;
		}
		
		
		return hasSwitched;
	}

	public CardZone getCardZoneByType(String type) {
		return this.playerDeck.getCardZoneByType(type);
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
		return Objects.equals(session, other.session);
	}

}
