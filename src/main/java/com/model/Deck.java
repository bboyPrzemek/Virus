package com.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Deck {

	private List<Card> stack;
	private List<Card> pile;

	public Deck() {
		this.stack = CardFactory.getDeck();
		this.pile = new ArrayList<>();
		shuffle();
	}

	public List<Card> getFromStack(int numOfCards) {
		List<Card> cardsForReturn = new ArrayList<>();

		for (int i = 0; i < numOfCards; i++) {
			if (this.stack.size() == 0) {
				movePileToStack();
			}
			cardsForReturn.add(this.stack.get(0));
			this.stack.remove(0);
		}
		return cardsForReturn;
	}

	public void addToPile(List<Card> cards) {
		List<Card> cardsToPile = new ArrayList<>();
		
		for (Card card : cards) {
			if (card.getOrganType().name() == "SPECIAL") {
				card.setTemporaryOrganType(null);
			}
			cardsToPile.add(card);
		}
		
		this.pile.addAll(cardsToPile);
	}

	public void movePileToStack() {
		this.stack.addAll(this.pile);
		shuffle();
		this.pile.clear();
	}

	public void shuffle() {
		Collections.shuffle(stack);
	}

}
