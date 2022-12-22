package com.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDeck {
	private List<Card> heart;
	private List<Card> maw;
	private List<Card> bone;
	private List<Card> brain;

	public PlayerDeck() {
		this.heart = new ArrayList<>();
		this.maw = new ArrayList<>();
		this.bone = new ArrayList<>();
		this.brain = new ArrayList<>();

	}

	// walidacje trzeba zrobic jeszcze czy wgl mozna dodac taka karte
	public void addCard(List<Card> cards) {
		List<Card> returnCards = new ArrayList<>();

		for (Card card : cards) {
			Boolean cardAdded = false;
			if (card.getOrganType().name() == "HEART") {
				if (deckValidator(card, this.heart)) {
					this.heart.add(card);
					cardAdded = true;
				}
			} else if (card.getOrganType().name() == "MAW") {
				if (deckValidator(card, this.maw)) {
					this.maw.add(card);
					cardAdded = true;
				}
			} else if (card.getOrganType().name() == "BONE") {
				if (deckValidator(card, this.bone)) {
					this.bone.add(card);
					cardAdded = true;
				}
			} else if (card.getOrganType().name() == "BRAIN") {
				if (deckValidator(card, this.brain)) {
					this.brain.add(card);
					cardAdded = true;
				}
			}
			if (!cardAdded) {
				returnCards.add(card);
			}
		}
	}

	public Boolean deckValidator(Card card, List<Card> cardList) {
		if (card.getCardType().name() != "ORGAN" && cardList.size() == 0) {
			return false;
		} else if (card.getCardType().name() == "ORGAN" && cardList.size() > 0) {
			return false;
		} else if (cardList.size() == 3) {
			return false;
		} else if (card.getCardType().name() == "ACTION") {
			return false;
		}

		return true;

	}

	public List<Card> removeCards() {
		return null;
	}

}
