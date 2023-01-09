package com.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@ServerEndpoint("/basicEndpoint/{gameId}")
public class WebSocketServer {
	private static Map<String, Game> gameMap = new HashMap<>();

	@OnOpen
	public void open(@PathParam("gameId") String gameId, Session session) {
		GameDAO gameDAO = new GameDAO();
		Game game = gameDAO.getGameById(gameId);

		
		if (!gameMap.containsKey(game.getUniqueGameIdentifier())) {
			gameMap.put(game.getUniqueGameIdentifier(), game);
		}
		
		String username  = session.getRequestParameterMap().get("username").get(0);
		

		
		Player player = new Player(session, username);
		gameMap.get(gameId).addPlayer(player);
		try {
			
			session.getBasicRemote().sendText(MessageService.message(new ClientDataWrapper(gameMap.get(gameId), player, State.CONNECT)), true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		if (gameMap.get(gameId).getPlayerList().size() == gameMap.get(gameId).getNumOfPlayers()) {
			gameMap.get(gameId).play();
			for (Player p :gameMap.get(gameId).getPlayerList()) {
				System.out.println("..................");
				System.out.println(p.getSession().getBasicRemote());
				System.out.println(p.getSession().getId());
				System.out.println("...................");
				try {
					p.getSession().getBasicRemote().sendText(MessageService.message(new ClientDataWrapper(gameMap.get(gameId), p, State.START)), true);
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}

		//for (Session s : session.getOpenSessions()) {
	//		try {
		//		System.out.println(s.getBasicRemote());
				//s.getBasicRemote().sendText("new client joined", true);
		//	} catch (IOException e) {
				// TODO Auto-generated catch block
		//		e.printStackTrace();
		//	}
	//	}

	}

	@OnClose
	public void close(Session session) {
	
		
		for (String key : gameMap.keySet()) {
			Player player = gameMap.get(key).getPlayerBySession(session);
			if (player != null) {
				gameMap.get(key).removePlayer(player);
				System.out.println("players connected" + gameMap.get(key).getPlayerList().size());
			}
		}
		
		System.out.println("session closed");
		System.out.println(session.getBasicRemote());
	}

	@OnError
	public void onError(Throwable error, Session session) {
		error.printStackTrace();
	}

	@OnMessage
	public void onMessage(@PathParam("gameId") String gameId, String message, Session session) {
		ObjectMapper objectMapper = new ObjectMapper();
		ClientMessageConverter clientMessageConverter = null;
		try {
			System.out.println("try");
			 clientMessageConverter = objectMapper.readValue(message, ClientMessageConverter.class);
		} catch (JsonParseException e1) {
			System.out.println("try1");
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			System.out.println("try2");
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			System.out.println("try3");
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		System.out.println(message);
		System.out.println(gameId);
		if (clientMessageConverter.getMessage().equals("SKIP")) {
			gameMap.get(gameId).setNextPlayer();
		}else if (clientMessageConverter.getMessage().equals("CHANGE")) {
			Game game = gameMap.get(gameId);
			Player  player = game.getPlayerBySession(session);
			game.changePlayerCard(player, clientMessageConverter.getCards());
			
			
		}else if (clientMessageConverter.getMessage().equals("USE")) {
			Game game = gameMap.get(gameId);
			Player  player = game.getPlayerBySession(session);

			int cardIndex = clientMessageConverter.getCards()[0];
			int clientId = clientMessageConverter.getPlayers()[0];
			String zone = clientMessageConverter.getZone();
			
			
			game.useCard(player, game.getPlayerByClientId(clientId), cardIndex, zone);
			
		}
		
		for (Player p :gameMap.get(gameId).getPlayerList()) {
			try {
				p.getSession().getBasicRemote().sendText(MessageService.message(new ClientDataWrapper(gameMap.get(gameId), p, State.UPDATE)),true);
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
