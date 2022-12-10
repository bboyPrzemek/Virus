package com.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Card {
	
	private CardType cardType;
	private OrganType organType;
	private ActionType actionType;
	private String uniqueTypeID;
	private Boolean isVaccinated;
	
	
	public Card(CardType cardType, OrganType organType, ActionType actionType, String uniqueTypeID) {
		this.cardType = cardType;
		this.organType = organType;
		this.actionType = actionType;
		this.uniqueTypeID = uniqueTypeID;
	}


	@Override
	public String toString() {
		return "Card [cardType=" + cardType + ", organType=" + organType + ", actionType=" + actionType + 
				  ", uniqueTypeID=" + uniqueTypeID + ", isVaccinated=" + isVaccinated + "]";
	}

}




