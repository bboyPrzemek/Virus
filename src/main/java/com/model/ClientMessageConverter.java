package com.model;
import lombok.Getter;


@Getter
public class ClientMessageConverter {
	 public String message;
	 public int[] cards;
	 public int[] players;
	 public String zone;
	 
	 public ClientMessageConverter(String message, int[] cards, int[] players) {
		 this.message = message;
		 this.cards = cards;
		 this.players = players; 
	 }
	 
	 public ClientMessageConverter(String message, int[] cards, int[] players, String zone) {
		 this.message = message;
		 this.cards = cards;
		 this.players = players; 
		 this.zone  = zone;
	 }
	 
	 public ClientMessageConverter() {}
}
