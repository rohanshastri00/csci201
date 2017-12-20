package gameplay;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import networking.ServerThread;

public class Game {
	//initialize a new game 
	private String gameName;
	private int numPlayers;
	private Map<Player, ServerThread> players;
	private Vector<String> usernames;
	private Vector<ServerThread> userThreads;
	private Dealer dealer;
	private int number;
	
	public Game (String gameName_, int numPlayers_) {
		gameName = gameName_;
		numPlayers = numPlayers_;
		players = new HashMap<Player, ServerThread>();
		usernames = new Vector<String>();
		userThreads = new Vector<ServerThread>();
		dealer = new Dealer();
		//first players turn when game is initialized, so number = 0
		number = 0;
	}
	
	public boolean canGameStart() {
		if (players.size() == numPlayers) {
			return true;
		} 
		return false;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public Vector<ServerThread> getServerThreads(){
		return userThreads;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	public Map<Player, ServerThread> getPlayers() {
		return players;
	}

	public void setPlayers(Map<Player, ServerThread> players) {
		this.players = players;
	}
	
	//THIS IS FOR INCREMENTING THROUGH THE PLAYERS FOR BETTING, ROUNDS
	public int getNumber() {
		return number;
	}
	public void incrementNumber() {
		number++;
	}
	
	public void resetNumber() {
		number = 0;
	}
	
	public int getRemainingSpotsNeeded() {
		int numNeeded = numPlayers - usernames.size();
		return numNeeded;
	}
	//starter will always have the first thread
	public ServerThread getStarterThread() {
		//return players.get(usernames.get(0));
		return userThreads.get(0);
	}
	
	public String getMostRecentJoined() {
		return usernames.lastElement();
	}
	
	public Boolean checkJoined() {
		if (usernames.size() == numPlayers) {
			return true;
		}
		return false;
	}
	
	public void addUsername(String username) {
		usernames.add(username);
	}
	
	public void addServerThread (ServerThread st) {
		userThreads.add(st);
	}
	
	public Vector<String> getUsernames() {
		return usernames;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}
	
	
	//GAMEPLAY
	
	public void dealCards() {
		
		Vector<Card> deck = dealer.getDeck();
		
		for (Player p : players.keySet()) {
			Card c1 = deck.get(0);
			deck.remove(0);
			Card c2 = deck.get(0);
			deck.remove(0);
			p.setHand(c1, c2);
			//all players should have cards now
		}
		
		Card c1 = deck.get(0);
		deck.remove(0);
		Card c2 = deck.get(0);
		deck.remove(0);
		dealer.setDealerHand(c1, c2);
	}
	
	public Vector<Card> getHand(String username) {
		for (Player p : players.keySet()) {
			if (p.getUsername().equals(username)) {
				return p.getHand();
			}
		}
		return null; //error, name not found
	}
	
	public Vector<Card> getDealerHand() {
		return dealer.getDealerHand();
	}

	public Player getPlayer(String username) {
		for (Player p : players.keySet()) {
			if (p.getUsername().equals(username)) {
				return p;
			}
		}
		return null; //error, name not found
	}


}

