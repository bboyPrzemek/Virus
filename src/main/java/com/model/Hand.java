package com.model;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Hand {
	
	
	private List<Card> cards;
	private Boolean isPlayable = false;
	
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
