package com.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.websocket.Session;

import lombok.Getter;

@Getter
public class Game {

	private Set<Player> playerList;
	
	private int numOfPlayers;
	private String uniqueGameIdentifier;
	private int currentPlayerIndex = 0;


	private Deck deck;

	public Game(String uniqueGameIdentifier, int numOfPlayers) {
		this.playerList = new HashSet<>();
		this.numOfPlayers = numOfPlayers;
		this.uniqueGameIdentifier = uniqueGameIdentifier;
		this.deck = new Deck();

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
		setPlayersIds();
		dealCards();
		setStartingPlayer();
	}

	public void setPlayersIds() {
		int i = 0;
		for (Player p : this.playerList) {
			p.setClientID(i);
			i++;
		}
	}

	public Player getPlayerByIndex(int index) {
		int i = 0;
		Player p = null;
		for (Player player : this.playerList) {
			if (index == i) {
				p = player;
			}
			i++;
		}
		return p;

	}

	public Player getPlayerByClientId(int clientId) {
		Player player = null;
		for (Player p : this.playerList) {
			if (p.getClientID() == clientId) {
				player = p;
			}

		}
		return player;
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

	public void useCard(Player fromPlayer, Player toPlayer, int index, String zone) {
		if (!fromPlayer.getIsPlayable())
			return;
		cardHandler(fromPlayer, toPlayer, index, zone);
	}

	public void cardHandler(Player fromPlayer, Player toPlayer, int index, String zone) {
		Card card = fromPlayer.getCardByIndex(index); // removes card from playerHand
		// Boolean cardPlayed = false;

		if (card.getCardType().name() == "ACTION") {
			actionCardHandler(fromPlayer, toPlayer, card, zone);
		} else if (card.getOrganType().name() == "SPECIAL") {
			specialCardHandler(fromPlayer, toPlayer, card, zone);
		} else {
			defaultCardHandler(fromPlayer, toPlayer, card);
		}
	}

	public Boolean defaultCardHandler(Player fromPlayer, Player toPlayer, Card card) {
		Boolean cardsAdded = toPlayer.updatePlayerDeck(card);

		if (cardsAdded) {
			fromPlayer.playCard(card);
			fromPlayer.drawCards(this.deck.getFromStack(1));
			this.deck.addToPile(toPlayer.organizePlayerDeck());
			setNextPlayer();
		}
		return cardsAdded;
	}

	public void actionCardHandler(Player fromPlayer, Player toPlayer, Card card, String zone) {

		switch (card.getActionType().name()) {
		case "BODY_CHANGE":
			changeBodyCardHandler(fromPlayer, toPlayer, card);
			break;
		case "GLOVE":
			useGlovesCard(fromPlayer, card);
			break;
		case "THIEF":
			organThiefCardHandler(fromPlayer, toPlayer, card, zone);
			break;
		case "EXCHANGE":
			switchOrgans(fromPlayer, toPlayer, card, zone);
			break;
		default:
		}
	}

	public Boolean specialCardHandler(Player fromPlayer, Player toPlayer, Card card, String zone) {
		
		try {
			card.setTemporaryOrganType(OrganType.valueOf(zone.toUpperCase()));
		} catch (Exception e) {
			return false;
		}

		Boolean cardsAdded = toPlayer.updatePlayerDeck(card);

		if (cardsAdded) {
			fromPlayer.playCard(card);
			fromPlayer.drawCards(this.deck.getFromStack(1));
			this.deck.addToPile(toPlayer.organizePlayerDeck());
			setNextPlayer();
		} else {
			card.setTemporaryOrganType(null);
		}
		return cardsAdded;

	}

	public Boolean organThiefCardHandler(Player thief, Player victim, Card card, String zone) {
		Boolean cardsMoved = false;
		cardsMoved = victim.moveZoneToOtherPlayer(thief, zone);

		System.out.println("cardsMoved ? " + cardsMoved);

		if (cardsMoved) {
			thief.playCard(card);
			this.deck.addToPile(Arrays.asList(card));
			thief.drawCards(this.deck.getFromStack(1));
			setNextPlayer();
		}
		return cardsMoved;
	}

	public Boolean switchOrgans(Player thief, Player victim, Card card, String zone) {
		Boolean hasSwitched = false;
		hasSwitched = victim.switchOrganWithOtherPlayer(thief, zone);

		if (hasSwitched) {
			thief.playCard(card);
			this.deck.addToPile(Arrays.asList(card));
			thief.drawCards(this.deck.getFromStack(1));
			setNextPlayer();
		}

		return hasSwitched;
	}

	public void changeBodyCardHandler(Player fromPlayer, Player toPlayer, Card card) {
		this.deck.addToPile(Arrays.asList(card));
		fromPlayer.playCard(card);
		PlayerDeck copyPlayer1Deck = new PlayerDeck(fromPlayer.getPlayerDeck());
		PlayerDeck copyPlayer2Deck = new PlayerDeck(toPlayer.getPlayerDeck());

		fromPlayer.setPlayerDeck(copyPlayer2Deck);
		toPlayer.setPlayerDeck(copyPlayer1Deck);
		fromPlayer.drawCards(this.deck.getFromStack(1));
		setNextPlayer();
	}

	public void useGlovesCard(Player player, Card card) {
		player.playCard(card);
		this.deck.addToPile(Arrays.asList(card));
		player.drawCards(this.deck.getFromStack(1));

		for (Player p : this.playerList) {
			if (p == player) {
				continue;
			}
			this.deck.addToPile(p.removeAllCards());
			p.drawCards(this.deck.getFromStack(3));
		}
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

	public void changePlayerCard(Player player, int[] cardIndexes) {
		if (!player.getIsPlayable())
			return;
		this.deck.addToPile(player.removeCards(cardIndexes));
		int numberOfPlayerCards = player.getHand().getCards().size();
		System.out.println("liczba kart aktualnie w rece " + numberOfPlayerCards);
		player.drawCards(this.deck.getFromStack(3 - numberOfPlayerCards));

		setNextPlayer();

	}

	public Player getPlayerByID(int ID) {
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
