package com.model;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import com.model.MessageService.MessageWrapper;
import com.services.GameService;

public class Main {
	
	
	public static void main(String[] args) {
		
		//GameService.createNewGame();
		//GameService.searchGame("0fd09b18-00fe-4af5-9ce6-0b93a50ed6f7");
		GameDAO gamedao = new GameDAO();
		Game g = gamedao.getGameById("221e076d-aed4-4eb3-a3ae-352661549c77");
		Game g2 = new Game("1",2);
		g2.addPlayer(new Player());
		g2.addPlayer(new Player());
		g2.play();
		System.out.println("stacksize" + g2.getDeck().getStack().size());
		
		
		
		
		
		MessageService messageService = new MessageService();
		//MessageService.MessageWrapper mw = messageService.new MessageWrapper(State.CONNECT, g2);
		
	
		//System.out.println(g.getUniqueGameIdentifier());
		//System.out.println(g.getNumOfPlayers());
		
		//System.out.println(ClassLayout.parseClass(Player.class).toPrintable());
		
	//	Card card = new Card(CardType.ACTION, OrganType.BONE, ActionType.BODY_CHANGE, "kua","k");
		
	
		
		
		
		
	}
}
