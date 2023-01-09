package com.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
	
	
	public static void main(String[] args) {
		
		//GameService.createNewGame();
		//GameService.searchGame("0fd09b18-00fe-4af5-9ce6-0b93a50ed6f7");
		PlayerDeck deck1 = new PlayerDeck();
		OrganType or = OrganType.valueOf("BRAIN");
		System.out.println(or.name());
		
		//deck1.addCard(Arrays.asList(new Card(CardType.ORGAN, OrganType.BONE, ActionType.none, "")));
		
		
		PlayerDeck deck2 = new PlayerDeck(deck1);
		//deck1.getBone().clear();
		
		List<String> s = new ArrayList<>();
		
		
		
		
		
		
		
		//deck1.setBone(null);
		System.out.println();
		//System.out.println(d2.getBone().size());
		
		
		
		
	
		
	
		//System.out.println(g.getUniqueGameIdentifier());
		//System.out.println(g.getNumOfPlayers());
		
		//System.out.println(ClassLayout.parseClass(Player.class).toPrintable());
		
	//	Card card = new Card(CardType.ACTION, OrganType.BONE, ActionType.BODY_CHANGE, "kua","k");
		
	
		
		
		
		
	}
}
