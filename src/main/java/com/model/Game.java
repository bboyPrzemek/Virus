package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.websocket.Session;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

public class Game {

	private Set<Player> playerList;
	@Getter
	private int numOfPlayers;
	private String uniqueGameIdentifier;
	private int currentPlayerIndex = 0;

	@JsonIgnore
	@Getter
	private Deck deck;

	public Game(String uniqueGameIdentifier, int numOfPlayers) {
		this.playerList = new HashSet<>();
		this.numOfPlayers = numOfPlayers;
		this.uniqueGameIdentifier = uniqueGameIdentifier;
		this.deck = new Deck();

	}

	public String getUniqueGameIdentifier() {
		return uniqueGameIdentifier;
	}

	public void addPlayer(Player player) {
		if (this.playerList.size() < this.numOfPlayers) {
			this.playerList.add(player);
		}

		if (this.playerList.size() == this.numOfPlayers) {
			setPlayersPositions();
		}
	}

	public Player getPlayerBySession(Session session) {
		for (Player player : this.playerList) {
			if (player.getSession() == session) {
				return player;
			}
		}
		return null;
	}

	public void removePlayer(Player player) {
		this.playerList.remove(player);
	}

	public void play() {
		dealCards();
		setStartingPlayer();
	}
	
	public Player getPlayerByIndex(int index) {
		int i = 0;
		Player p = null;
		for (Player player : this.playerList) {
			if (index == i) {
				p = player;
			}
			i ++;
		}
		return p;

	}

	public void setStartingPlayer() {

		Player player = null;

		for (Player p : this.playerList) {
			player = p;
			break;
		}
		player.setIsPlayable();
	}

	public void setNextPlayer() {
		this.currentPlayerIndex++;
		int index = 0;
		Map<Integer, Player> playerMap = new HashMap<>();

		for (Player p : this.playerList) {
			p.setIsNonPlayable();
			playerMap.put(index, p);
			index++;
		}

		if (this.currentPlayerIndex == this.playerList.size()) {
			this.currentPlayerIndex = 0;
		}

		playerMap.get(this.currentPlayerIndex).setIsPlayable();
	}

	public void dealCards() {
		for (Player player : this.playerList) {
			player.drawCards(this.deck.getFromStack(3));
		}
		System.out.println("deck" + this.deck.getStack().size());
	}

	public void useCard(Player fromPlayer, Player toPlayer, int index) {
		List<Card> cards = new ArrayList<>();
		Card card = fromPlayer.playCard(index);
		cards.add(card);
		toPlayer.updatePlayerDeck(cards);
		fromPlayer.drawCards(this.deck.getFromStack(1));
		setNextPlayer();
		
	}

	public Set<Player> getPlayerList() {
		return this.playerList;
	}

	public void setPlayersPositions() {
		int[][] positions = LayoutConfig.getConfig(this.numOfPlayers - 2);
		int i = 0;

		for (Player player : this.playerList) {
			player.setPosX(positions[i][0]);
			player.setPosY(positions[i][1]);
			i++;
		}
	}

	public void changePlayerCard(Player player, List<Integer> cardIndexes) {
		this.deck.addToPile(player.removeCards(cardIndexes));
		int numberOfPlayerCards = player.getHand().getCards().size();
		System.out.println("liczba kart aktualnie w rece " + numberOfPlayerCards);
		player.drawCards(this.deck.getFromStack(3 - numberOfPlayerCards));

		setNextPlayer();

	}

	public Player getPlayerByID(String ID) {
		for (Player player : playerList) {
			if (player.getClientID() == ID) {
				return player;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "Game [playerList=" + playerList + ", numOfPlayers=" + numOfPlayers + ", uniqueGameIdentifier="
				+ uniqueGameIdentifier + "]";
	}

}
