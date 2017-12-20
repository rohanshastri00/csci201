package networking;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import gameplay.Card;
import gameplay.Dealer;
import gameplay.Game;
import gameplay.Player;
import messages.DealtCardsMessage;
import messages.FinalizeTurnMessage;
import messages.Message;
import messages.RequestActionMessage;
import messages.RequestBetMessage;
 

public class BlackjackServer {
	/*keeps track of all the current games in progress
	and the players associated with each game */
	private static Vector<ServerThread> serverThreads = new Vector<ServerThread>();
	private static Vector<Game> games = new Vector<Game>();
	
	public BlackjackServer(int port) {
		
		try {
			System.out.println("Binding to port " + port);
			ServerSocket ss = new ServerSocket(port);
			System.out.println("Bound to port " + port);
			while(true) {
				Socket s = ss.accept(); // blocking
				System.out.println("Connection from: " + s.getInetAddress());
				ServerThread st = new ServerThread(s, this);
				serverThreads.add(st);
			}
		} catch (IOException ioe) {
			System.out.println("ioe in ChatRoom constructor: " + ioe.getMessage());
		}
	}
	
	//broadcast to everyone
	public void broadcast(Message cm, Game g) {
		if (cm != null) {
			for(ServerThread thread : g.getServerThreads()) {
				thread.sendMessage(cm);
			}
		}
	}
	
	//broadcast to everyone except for starter
	public void excludingStarterBroadcast(Message cm, String gameName) {
		Game current = null;
		if (cm != null) {
			for (Game g : games) {
				if(g.getGameName().equals(gameName)) {
					current = g;
				}
			}
			Vector <ServerThread> threads = current.getServerThreads();
			//STARTING AT 1 PREVENTS THE STARTER FROM GETTING THE MESSAGE
			for (int i = 1; i < threads.size();i++) {
				threads.get(i).sendMessage(cm);
			}
		}
	}
	//broadcast to everyone but serverThread included
	public void	excludingBroadcast(Message m, ServerThread st, Vector<ServerThread> threads) {
		if (m != null) {
			for (int i = 0; i < threads.size(); i++) {
				if (!(st.equals(threads.get(i)))) {
					threads.get(i).sendMessage(m);
				}
			}
		}
	}

	
	

	//broadcast specifically to the thread in parameter
	public void specificBroadcast(Message cm, ServerThread st) {
		if (cm != null) {
			for(ServerThread thread : serverThreads) {
				if (st == thread) {
					thread.sendMessage(cm);
					break;
				}
			}
		}
	}

	//gets the thread of user who created the game
	public ServerThread getStarterThread(String gameName) {
		for (Game g : games) {
			if (g.getGameName().equals(gameName)) {
				return g.getStarterThread();
			}
		}
		return null;
	}
	
	public static Vector<ServerThread> getServerThreads() {
		return serverThreads;
	}
	
	public Vector <Game> getGames() {
		return games;
	}
	

	public int CheckGameName(String gameName, int numPlayers) { 
		for (Game g : games) {
			if (gameName.equals(g.getGameName())) {
				return 0; //false -  game exists
			}
		}
		
		if (numPlayers != -1) { //here you're not joining you're starting
			Game g = new Game (gameName,numPlayers);
			games.add(g);
			return 1; //true - game didn't exist, now it's in the vector
		}
		return 2; //false - game doesn't exist, did not add (joining)
	}
	
	public int CheckUsername(String gameName, String username, ServerThread st) {
		for (Game g : games) {
			if (gameName.equals(g.getGameName())) {		
				Map<Player, ServerThread> players  = g.getPlayers();
				for (Player key : players.keySet()) {
					if (key.getUsername().equals(username)) {
						//if name exists in the game, name exists - false
						return 0; 
					}
				}
				g.addUsername(username);
				g.addServerThread(st);
				Player p = new Player(username, gameName);
				players.put(p, st);
				return 1; //true - added new thread and str to map
			}
		}
		return -1; //didn't find the right game
	}

	
	public int getRemainingPlayersLeft(String gameName) {
		for (Game g : games) {
			if (g.getGameName().equals(gameName)) {
				return g.getRemainingSpotsNeeded();
			}
		}
		return -1;
	}
	
	public void startGame (Game g, ServerThread st, Vector<ServerThread> threads) {
		//shuffle cards
		Dealer d = g.getDealer();
		d.shuffle();
		Message m = new Message("Dealer is shuffling cards...");
		broadcast(m,g);
		Map<Player, ServerThread> players = g.getPlayers();
		for (Player p : players.keySet()) {
			//if the name in the player map equals the thread being passed in
			if (players.get(p).equals(st)) {
				String status = p.getUsername() + ", it is your turn to make a bet. Your chip total is " + p.getChipTotal(); 
				RequestBetMessage rbm = new RequestBetMessage(status, p.getGameName(), 0, p.getUsername());
				st.sendMessage(rbm);
				
				//send message to other players that it is someone elses turn to bet
				String notify = "It is " + p.getUsername() + "'s turn to make a bet.";
				Message group = new Message(notify);
				excludingBroadcast(group, st, threads);
				
			}
		}	
	}
	
	public void continueTakingBets(Game g, ServerThread st, Vector<ServerThread> threads) {
		Map<Player, ServerThread> players = g.getPlayers();
		for (Player p : players.keySet()) {
			//if the name in the player map equals the thread being passed in
			if (players.get(p).equals(st)) {
				String status = p.getUsername() + ", it is your turn to make a bet. Your chip total is " + p.getChipTotal(); 
				RequestBetMessage rbm = new RequestBetMessage(status, p.getGameName(), 0, p.getUsername());
				st.sendMessage(rbm);
				
				//send message to other players that it is someone elses turn to bet
				String notify = "It is " + p.getUsername() + "'s turn to make a bet.";
				Message group = new Message(notify);
				excludingBroadcast(group, st, threads);		
			}
		}
	}
	
	
	public Boolean checkBetAmount(String gameName, String username, int betAmount) {
		for (Game g : games) {
			if (g.getGameName().equals(gameName)) {
				Map<Player, ServerThread> players  = g.getPlayers();
				for (Player p : players.keySet()) {
					if (username.equals(p.getUsername())) {
						int chipTotal = p.getChipTotal();
						if ((chipTotal - betAmount) > 0) {
							return true;
						} else {
							return false;
						}
					}
				}
			}
		}
		return null;
	}
	
	//checks only if port specified is valid -- server side 
	public static void main(String [] args) {
		System.out.println("Welcome to the Black Jack Server!");
		System.out.println("Please enter a port:" );
		boolean isValidPort = false;
		Scanner scan = new Scanner(System.in);
		int port = 0;
		//must be in while loop if port is invalid
		while (!isValidPort) {
			port = scan.nextInt();
			if (port > 1023 && port < 49152) {
				isValidPort = true;
				System.out.println("Successfully started the Black Jack Server on port " + port);
			} else {
				System.out.println("Invalid port number.");
			}
		}
		BlackjackServer bjcr = new BlackjackServer(port);
		
	}

	public void dealCards(Game g) {
		g.dealCards();
		Map<Player, ServerThread> players  = g.getPlayers();
		
		String fullString = "---------------------------------------------------------------";
		Dealer d = g.getDealer();
		Vector<Card> dealerHand = d.getDealerHand();
		fullString += "\nDEALER\n";
		String dealerc2str = dealerHand.get(1).getName() + " of " + dealerHand.get(1).getSuit();
		fullString += "\nCards: | ? | " + dealerc2str + " |\n";
		fullString += "---------------------------------------------------------------\n";
		
		for (Player p: players.keySet()) {
			fullString += "---------------------------------------------------------------";
			
			fullString += "\nPlayer: " + p.getUsername() + "\n\n";

			Vector<Card> currentHand = p.getHand();
			String c1str = currentHand.get(0).getName() + " of " + currentHand.get(0).getSuit();
			String c2str = currentHand.get(1).getName() + " of " + currentHand.get(1).getSuit();
			
			Vector<Integer> potentialValues = p.getInitialHandValues();
			if (potentialValues.size() == 1) {
				fullString += "Status: " + potentialValues.get(0) +"\n";
			}
			//else should be 2
			else {
				fullString += "Status: " + potentialValues.get(0) + " or " + potentialValues.get(1) +"\n";
			}

			fullString += "Cards: | " + c1str + " | " + c2str + " |\n";
			
			
			fullString += "Chip total: " + p.getChipTotal() + " Bet Amount: " + p.getBetAmount();
		
			fullString += "\n---------------------------------------------------------------\n";
		}
		
		
		broadcast(new DealtCardsMessage(fullString, g.getGameName()), g);
		//now go back to first player and start asking to hit or stay
	
	}

	public void beginRound(Game g, ServerThread st, Vector<ServerThread> threads) {
		Map<Player, ServerThread> players = g.getPlayers();
		for (Player p : players.keySet()) {
			//if the name in the player map equals the thread being passed in
			if (players.get(p).equals(st)) {
				String status = "It is your turn to add cards to your hand\nEither enter '1' or 'stay' to stay. "
						+ "Enter either or '2' or 'hit' to hit."; 
				RequestActionMessage ram = new RequestActionMessage(status, g.getGameName(), "", p.getUsername());
				//send response to first/next player in round
				st.sendMessage(ram);
				
				//send message to other players that it is someone elses turn to bet
				String notify = "It is " + p.getUsername() + "'s turn to to add cards to their hand.";
				Message group = new Message(notify);
				excludingBroadcast(group, st, threads);
				
			}
		}	
	}

	public void hit(Game g, ServerThread st, String username, Vector<ServerThread> threads) {
		Map<Player, ServerThread> players = g.getPlayers();
		String result = "";
		for (Player p : players.keySet()) {
			//if the name in the player map equals the thread being passed in
			if (players.get(p).equals(st)) {
				//get players hand
				Vector<Card> hand = p.getHand();
				
				//add a new card, the "hit" functionality
				Card c1 = g.getDealer().getDeck().get(0);
				g.getDealer().getDeck().remove(0);
				hand.add(c1);
				
				//create messages explaining what just happened
				String self = "You hit. You were dealt the " + c1.getName() + " of " + c1.getSuit();
				String group = p.getUsername() + " hit. They were dealt the " + c1.getName() + " of " + c1.getSuit();
				
				//calculate the new values for the user's hand
				Vector<Integer> updatedValues = p.calculateUpdatedValue(c1);
				
				for (int i = 0; i < updatedValues.size();i++) {
					if(updatedValues.get(i) > 21) { //remove any busts
						updatedValues.remove(i);
					}
				}
				
				if (updatedValues.size() == 0) { //flat busted
					result = "busted";
				}
				
				for (int i = 0; i < updatedValues.size(); i++) { //check for blackjacks					
					if (updatedValues.get(i) == 21) {
						result = "blackjack";
					}
				}
				
				//now only option is ongoing
				if (result.equals("")) {
					result = "ongoing";
				}
				
				
				//notify user of what they did
				st.sendMessage(new RequestActionMessage(self, g.getGameName(), result, username));
				
				//send message to other players that it is someone elses turn to bet
				Message notify = new Message(group);
				excludingBroadcast(notify, st, threads);
				
				
				if (result.equals("busted")) {
					p.setIsBusted(true);
					String selfBustedString = "You busted. " + p.getBetAmount() + " chips were deducted from your total.";
					st.sendMessage(new RequestActionMessage(selfBustedString, g.getGameName(), result, username));

					
					String groupBustedString = p.getUsername() + " busted. " + p.getBetAmount() + " chips were deducted from " + p.getUsername()  + "'s total.";
					Message bustedNotify = new Message(groupBustedString);
					excludingBroadcast(bustedNotify, st, threads);
					
					//mark the end of this guys turn
					FinalizeTurnMessage ftm = new FinalizeTurnMessage(g.getGameName(), p.getUsername(), result);
					st.sendMessage(ftm);
				}
				
				else if (result.equals("ongoing")) {
					//reprint options, get user input
					String status = "It is your turn to add cards to your hand\nEither enter '1' or 'stay' to stay. "
							+ "Enter either or '2' or 'hit' to hit."; 
					RequestActionMessage ram = new RequestActionMessage(status, g.getGameName(), "", p.getUsername());
					//send response to first/next player in round
					st.sendMessage(ram);
				}
				
				else if (result.equals("blackjack")) {
					FinalizeTurnMessage ftm = new FinalizeTurnMessage(g.getGameName(), p.getUsername(), result);
					st.sendMessage(ftm);
				}
				
			}
		}
	}

	public void stay(Game g, ServerThread st, String username, Vector<ServerThread> threads) {
		Map<Player, ServerThread> players = g.getPlayers();
		for (Player p : players.keySet()) {
			if (players.get(p).equals(st)) {
				p.setIsStaying(true);
			}
		}
		//strings created
		String self = "You stayed.";
		String group = username + " stayed.";
		//current player notified, turn is finalized so count incremented
		st.sendMessage(new Message(self));
		FinalizeTurnMessage ftm = new FinalizeTurnMessage(g.getGameName(), username, "");
		st.sendMessage(ftm);
		//group notified
		Message notify = new Message(group);
		excludingBroadcast(notify, st, threads);
	}
	
	public void printHand(Game g, ServerThread st, String username, Vector<ServerThread>threads, String status) {
		String fullString = "";
		Map<Player, ServerThread> players = g.getPlayers();
		for (Player p : players.keySet()) {
			//if the name in the player map equals the thread being passed in
			if (players.get(p).equals(st)) {
				//get players hand
				Vector<Card> hand = p.getHand();
				fullString += "---------------------------------------------------------------";
				
				fullString += "\nPlayer: " + p.getUsername() + "\n\n";
				
				Vector<Integer> potentialValues = p.getInitialHandValues();
				int temp = 0;
				int highest = 0;
				for (int i = 0; i < potentialValues.size();i++) {
					temp = potentialValues.get(i);
					if (temp > highest) {
						highest = temp;
					}
				}
				p.setFinalHand(highest);
				
				if (status.equals("")) {
					fullString += "Status: " + highest + "\n";
				}
				else {
					fullString += "Status: " + highest + " - " + status + "\n";
				}
				
				Vector<Card> currentHand = p.getHand();
				
				fullString += "Cards: | ";
				for (int i = 0; i < currentHand.size();i++) {
					String cardstr = currentHand.get(i).getName() + " of " + currentHand.get(i).getSuit();
					fullString += (cardstr + " | ");
				}
				fullString += "\n";				
				
				fullString += "Chip total: " + p.getChipTotal() + " Bet Amount: " + p.getBetAmount();
			
				fullString += "\n---------------------------------------------------------------\n";
			}
		} //end of finding player
		
		broadcast(new Message(fullString), g);
	}

	public void dealerTurn(Game g) {
		String dealerNotify = "It is now time for the dealer to play.";
		broadcast(new Message(dealerNotify),g);
		
		Vector<Card> dealerHand = g.getDealerHand();
		Dealer d = g.getDealer();
		Vector<Integer> potentialValues = d.getInitialHandValues(dealerHand);
		
		int temp = 0;
		int highest = 0;
		for (int i = 0; i < potentialValues.size();i++) {
			temp = potentialValues.get(i);
			if (temp > highest) {
				highest = temp;
			}
		}
		String result = "";
		if (highest >= 17) {
			String dealerStays = "Dealer stays.";
			broadcast(new Message(dealerStays), g);
			d.setFinalHand(highest);
			result = "stay";
			d.setIsStaying(true);
		}
		else { //dealer must hit
			//while hasnt busted or gone past 17
			while (result == "") {
				//pop the top
				Card c1 = d.getDeck().get(0);
				d.getDeck().remove(0);
				dealerHand.add(c1);
				
				String group = "Dealer hit. They were dealt the " + c1.getName() + " of " + c1.getSuit();
				broadcast(new Message(group), g);

				//calculate the new values for the dealer's hand
				Vector<Integer> updatedValues = d.calculateUpdatedValue(c1);
				
				for (int i = 0; i < updatedValues.size();i++) {
					if(updatedValues.get(i) > 21) { //remove any busts
						updatedValues.remove(i);
					}
				}
				
				if (updatedValues.size() == 0) { //flat busted
					result = "busted";
					d.setIsBusted(true);
					break;
				}
				
				for (int i = 0; i < updatedValues.size(); i++) { //check for blackjacks					
					if (updatedValues.get(i) == 21) {
						result = "blackjack";
					}
				}
				//find best possible card
				temp = 0;
				highest = 0;
				for (int i = 0; i < updatedValues.size();i++) {
					temp = updatedValues.get(i);
					if (temp > highest) {
						highest = temp;
					}
				}

				if (highest >= 17) {
					result = "stay";
					d.setIsStaying(true);
				}
			}
		} //end of else 
		
		String fullString = "---------------------------------------------------------------";
		fullString += "\nDEALER\n\n";
		d.setFinalHand(highest);
		if (result.equals("stay")) {
			fullString += "Status: " + highest + "\n";
		}
		else {
			fullString += "Status: " + highest + " - " + result + "\n";
		}
		
		Vector<Card> currentHand = d.getDealerHand();
		
		fullString += "Cards: | ";
		for (int i = 0; i < currentHand.size();i++) {
			String cardstr = currentHand.get(i).getName() + " of " + currentHand.get(i).getSuit();
			fullString += (cardstr + " | ");
		}
		fullString += "\n";				
		fullString += "\n---------------------------------------------------------------\n";
		
		broadcast(new Message(fullString), g);

		calculateTotals(g);
	}

	public void calculateTotals(Game g) {
		Map<Player, ServerThread> players = g.getPlayers();
		Dealer d = g.getDealer();
		for (Player p : players.keySet()) {
			if ((p.getFinalHand() > d.getFinalHand()) && (p.getIsStaying()) && (d.getIsStaying())) {
				//give chips
				int value = p.getChipTotal() + p.getBetAmount();
				p.setChipTotal(value);
			}
			else if (p.getFinalHand() < d.getFinalHand() && (p.getIsStaying()) && (d.getIsStaying())) {
				//remove chips
				int value = p.getChipTotal() - p.getBetAmount();
				p.setChipTotal(value);
			}
			else if (p.getFinalHand() == d.getFinalHand() && (p.getIsStaying()) && (d.getIsStaying())) {
				//return chips
				int value = p.getChipTotal();
				p.setChipTotal(value);
			}
			else if ((d.getIsBusted()) && (p.getIsStaying()))	{
				//give chips
				int value = p.getChipTotal() + p.getBetAmount();
				p.setChipTotal(value);
			}
			else if (p.getIsBusted()){
				//remove chips
				int value = p.getChipTotal() - p.getBetAmount();
				p.setChipTotal(value);
			}
			
		String status = "";
		String calculation = "";
		if (p.getFinalHand() > 21) { status = "busted"; }
		if (p.getFinalHand() == 21) { status = "blackjack"; }
		if (status.equals("")) {
			calculation = p.getUsername() + " had " + p.getFinalHand() + ". " 
					+ p.getBetAmount() + " chips were added to " + p.getUsername() + "'s total.";
		}
		else if (status.equals("busted")){
			calculation = p.getUsername() + " busted. " 
					+ p.getBetAmount() + " chips were deducted from " + p.getUsername() + "'s total.";
		}
		else if (status.equals("blackjack")) {
			calculation = p.getUsername() + " had blackjack. " 
					+ p.getBetAmount() + " chips were added to " + p.getUsername() + "'s total.";
		}
		
		broadcast(new Message(calculation), g);
		}
	}
}
