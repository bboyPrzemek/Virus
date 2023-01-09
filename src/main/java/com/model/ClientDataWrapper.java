package com.model;
import java.util.ArrayList;
import java.util.List;

import com.model.Game;
import com.model.Hand;
import com.model.Player;
import com.model.PlayerDeck;

import lombok.Getter;


@Getter
public class ClientDataWrapper {
	
	private List<PlayerData> playerData;
	private State state;
	
	public ClientDataWrapper(Game game, Player player, State state) {
		playerData = new ArrayList<>();
		this.state = state;
		buildPlayerDataResponse(game, player);
	}
	
	
	public void buildPlayerDataResponse(Game game, Player player) {
		for (Player p : game.getPlayerList()) {
			if (p == player) {
				playerData.add(new PlayerData(p.getHand(), p.getPlayerDeck(), p.getName(), p.getIsPlayable(), p.getClientID()));
			}else {
				playerData.add(new PlayerData(p.getPlayerDeck(), p.getName(), p.getIsPlayable(), p.getClientID()));
			}
		}
		
	}
	
	@Getter
	public class PlayerData{
		private Hand hand;
		private PlayerDeck playerDeck;
		private String name;
		private Boolean isPlayable = false;
		private int clientId;
		
		public PlayerData(PlayerDeck playerDeck, String name, Boolean isPlayable, int clientId) {
			this.playerDeck = playerDeck;
			this.name = name;
			this.isPlayable = isPlayable;
			this.clientId = clientId;
		}
		
		public PlayerData(Hand hand, PlayerDeck playerDeck, String name, Boolean isPlayable, int clientId) {
			this.hand = hand;
			this.playerDeck = playerDeck;
			this.name = name;
			this.isPlayable = isPlayable;
			this.clientId = clientId;
		}
	}
}

enum State{
	
	ERROR("0"),
	CONNECT("1"),
	START("2"),
	UPDATE("3"),
	CLOSE("4");
	
	
	private final String stateId;
	
	private State(String stateId) {
		this.stateId = stateId;
	}
	
	public String getStateId() {
		return this.stateId ;
	}
	
}
