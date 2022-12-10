package com.model;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Hand {
	
	@Setter
	@Getter
	private List<Card> cards;
	
	public Hand() {
		this.cards = new ArrayList<>();
	}
	
	public Card removeCard(int index) {
		Card card = cards.get(index);
		this.cards.remove(index);
		return card;
	}
	
	
	public void addCardsToHand(List<Card> cards) {
		this.cards.addAll(cards);
	}
}
