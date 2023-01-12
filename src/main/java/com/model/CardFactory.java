package com.model;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CardFactory {
	
	
	public static List<Card> getDeck() {
		Map<String, String> config = getConfig();
		List<Card> cards = getCards();
		List<Card> deck = new ArrayList<>();
		
		System.out.println(cards.size());
		for (Card c : cards) {
			int counter = Integer.valueOf(config.get(c.getUniqueTypeID()));
			System.out.println(counter);
			
			for (int i = 0; i < counter; i++) {
				deck.add(new Card(c.getCardType(), c.getOrganType(), c.getActionType(), c.getUniqueTypeID()));
			}
		}
		
		
		return deck;
	}

	public static List<Card> getCards() {
		InputStream inputStream = CardFactory.class.getClassLoader().getResourceAsStream(GameSettings.CARDS_FILE_PATH);
		Reader reader = new InputStreamReader(inputStream);
		
		
		List<Card> cardList = new ArrayList<>();
		JSONParser parser = new JSONParser();

		try {
			Object obj = parser.parse(reader);
			JSONArray cards = (JSONArray) obj;

			for (int i = 0; i < cards.size(); i++) {
				JSONObject jsonObject = (JSONObject) cards.get(i);

				CardType cardType = CardType.valueOf(jsonObject.get("cardType").toString());
				OrganType organType = OrganType.valueOf(jsonObject.get("organType").toString());
				ActionType actionType = ActionType.valueOf(jsonObject.get("actionType").toString());
				String uniqueTypeID = jsonObject.get("uniqueTypeID").toString();

				cardList.add(new Card(cardType, organType, actionType, uniqueTypeID));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cardList;
	}

	public static Map<String, String> getConfig() {
		Map<String, String> configMap = new TreeMap<String, String>();
		InputStream inputStream = CardFactory.class.getClassLoader().getResourceAsStream(GameSettings.CONFIG_FILE_PATH);
		Reader reader = new InputStreamReader(inputStream);

		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(reader);
			JSONArray configs = (JSONArray) obj;

			for (int i = 0; i < configs.size(); i++) {
				JSONObject jsonObject = (JSONObject) configs.get(i);
				configMap.put(jsonObject.get("uniqueTypeID").toString(), jsonObject.get("count").toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return configMap;
	}

}
