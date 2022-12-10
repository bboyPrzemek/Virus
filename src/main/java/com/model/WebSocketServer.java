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

import com.model.MessageService.MessageWrapper;

@ServerEndpoint("/basicEndpoint/{gameId}")
public class WebSocketServer {
	private static Map<String, Game> gameMap = new HashMap<>();

	@OnOpen
	public void open(@PathParam("gameId") String gameId, Session session) {
		GameDAO gameDAO = new GameDAO();
		Game game = gameDAO.getGameById(gameId);
		
		MessageService messageService = new MessageService();
		

		
		if (!gameMap.containsKey(game.getUniqueGameIdentifier())) {
			gameMap.put(game.getUniqueGameIdentifier(), game);
		}
		
		String username  = session.getRequestParameterMap().get("username").get(0);
		

		
		Player player = new Player(session, username);
		gameMap.get(gameId).addPlayer(player);
		try {
			MessageService.MessageWrapper mw = messageService.new MessageWrapper(State.CONNECT, gameMap.get(gameId), player);
			session.getBasicRemote().sendText(messageService.message(mw),true);
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
					MessageService.MessageWrapper mw = messageService.new MessageWrapper(State.START, gameMap.get(gameId), p);
					p.getSession().getBasicRemote().sendText(messageService.message(mw), true);
				
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
		MessageService messageService = new MessageService();
		System.out.println(message);
		System.out.println(gameId);
		if (message.equals("SKIP")) {
			gameMap.get(gameId).setNextPlayer();
		}else if (message.split(",")[0].equals("CHANGE")) {
			System.out.println("mleko");
			Game game = gameMap.get(gameId);
			Player  player = game.getPlayerBySession(session);
			System.out.println(player.getName());
			System.out.println(player.getClientID());
			String[] mess = message.split(",");
			List<Integer> integerList = new ArrayList<>();
			
			
			for (int i = 1; i < mess.length; i++) {
				integerList.add(Integer.valueOf(mess[i]));
				System.out.println(mess[i]);
			}
			
			
			game.changePlayerCard(player, integerList);
			
			
			
		}
		
		for (Player p :gameMap.get(gameId).getPlayerList()) {
			try {
				MessageService.MessageWrapper mw = messageService.new MessageWrapper(State.UPDATE, gameMap.get(gameId), p);
				p.getSession().getBasicRemote().sendText(messageService.message(mw), true);
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
