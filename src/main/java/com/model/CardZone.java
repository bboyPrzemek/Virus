package com.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardZone {
	private List<Card> cards;
	private String type;
	
	
	
	public CardZone(CardZone cardZone) {
		this.type = cardZone.getType();
		this.cards = new ArrayList<>();
		
		for (Card card : cardZone.getCards()) {
			this.cards.add(card);
		}
	}

	public CardZone(String type) {
		this.type = type;
		this.cards = new ArrayList<>();
	}

	public Boolean addCard(Card card) {
		Boolean canAddCards = canAddCards(card);
		if (canAddCards) {
			this.cards.add(card);
		}
		return canAddCards;
	}
	
	public void clearZone() {
		this.cards.clear();
	}

	public Boolean canAddCards(Card card) {
		if (card.getCardType().name() != "ORGAN" && isEmpty()) {
			return false;
		} else if (card.getCardType().name() == "ORGAN" && !isEmpty()) {
			return false;
		} else if (isFull()) {
			return false;
		} else if (card.getCardType().name() == "ACTION") {
			return false;
		}

		return true;
	}

	public List<Card> destroyZone() {
		List<Card> cards = new ArrayList<>();

		for (Card card : this.cards) {
			cards.add(card);
		}

		this.cards.clear();
		return cards;

	}
	
	public Boolean isEmpty() {
		return this.cards.size() == 0;
	}
	
	public Boolean isFull() {
		return this.cards.size() == 3;
	}

	public int countRemedyCardsNumber() {
		int i = 0;

		for (Card card : this.cards) {
			if (card.getCardType().name() == "REMEDY") {
				i++;
			}
		}
		return i;

	}
	
	public int countVirusCardsNumber() {
		int i = 0;

		for (Card card : this.cards) {
			if (card.getCardType().name() == "VIRUS") {
				i++;
			}
		}
		return i;

	}
	

	public List<Card> removeVirusAndRemedyFromBone() {
		List<Card> cards = new ArrayList<>();

		for (Card card : this.cards) {
			if (card.getCardType().name() == "VIRUS" || card.getCardType().name() == "REMEDY") {
				cards.add(card);
			}
		}
		
		this.cards.remove(2);
		this.cards.remove(1);

		return cards;
	}

	public Boolean isImmuned() {
		return this.cards.size() == 3;
	}
	
	
}
