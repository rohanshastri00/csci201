package networking;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import gameplay.Game;
import gameplay.Player;
import messages.CheckGameMessage;
import messages.CheckUsernameMessage;
import messages.DealtCardsMessage;
import messages.FinalizeTurnMessage;
import messages.JoinGameMessage;
import messages.Message;
import messages.RequestActionMessage;
import messages.RequestBetMessage;
import messages.StartGameMessage;
import objects.ValidGameName;
import objects.ValidUsername;

public class ServerThread extends Thread {
	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private BlackjackServer server;
	private int playerNumber = 0;
	public ServerThread(Socket s, BlackjackServer server) {
		try {
			this.server = server;
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe in ServerThread constructor: " + ioe.getMessage());
		}
	}

	public void sendMessage(Message cm) {
		try {
			oos.writeObject(cm);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	
	public void run() {
		try {
			while(true) {
				Object obj = ois.readObject();
				if (obj instanceof CheckGameMessage) { //it's checking the gameName
					CheckGameMessage cgm = (CheckGameMessage)obj;
					int valid = server.CheckGameName(cgm.getMessage(), cgm.getNumPlayers());
					ValidGameName vgn = new ValidGameName(valid);
					oos.writeObject(vgn);
					oos.flush();
				}
				
				else if (obj instanceof CheckUsernameMessage) {
					CheckUsernameMessage cum = (CheckUsernameMessage)obj;
					int valid = server.CheckUsername(cum.getMessage(), cum.getUsername(), this);
					ValidUsername vun = new ValidUsername(valid);
					oos.writeObject(vun);
					oos.flush();
	
				} 
				
				else if (obj instanceof JoinGameMessage) {
					JoinGameMessage jgm = (JoinGameMessage)obj;
					int playersLeft = server.getRemainingPlayersLeft(jgm.getMessage());
					jgm.setNumPlayersRemaining(playersLeft);	
					//broadcast to starter that this user is joining
					server.specificBroadcast(jgm, server.getStarterThread(jgm.getMessage()));
					//broadcasts to everyone but the starter
					if(jgm.getNumPlayersRemaining() == 0) {
						jgm.setAllPlayersJoined(true);
						server.excludingStarterBroadcast(jgm, jgm.getMessage());
					}		
				}
				
				else if (obj instanceof StartGameMessage) {
					StartGameMessage sgm = (StartGameMessage) obj;
					//pass in the server thread and the game object g
					Vector<Game> games = server.getGames();
					for (Game g : games) {
						//get the correct game
						if (g.getGameName().equals(sgm.getMessage())) {
							server.startGame(g, this, g.getServerThreads());
						}
					}
				}
				
				else if (obj instanceof RequestBetMessage) {
					RequestBetMessage rbm = (RequestBetMessage) obj;
					//updates chipTotal if bet is valid
					Boolean betOK = server.checkBetAmount(rbm.getGameName(), rbm.getUsername(), rbm.getBetAmount());
					if (betOK){
						//send messages to group regarding the bet amount
						String selfStatus = "You bet " + rbm.getBetAmount() + " chips";
						String groupStatus = rbm.getUsername() + " bet " + rbm.getBetAmount() + " chips";
						Message self = new Message (selfStatus);
						Message group = new Message (groupStatus);
						server.specificBroadcast(self, this);
						
						Vector<Game> games = server.getGames();
						for (Game g : games) {
							//get the correct game
							if (g.getGameName().equals(rbm.getGameName())) {
								//
								Player p = g.getPlayer(rbm.getUsername());
								p.setBetAmount(rbm.getBetAmount());
								
								server.excludingBroadcast(group, this, g.getServerThreads());
								rbm.setValidBet(true);
								
								//send call for next thread
								Vector<ServerThread> threads = g.getServerThreads();
								g.incrementNumber();
								if (g.getNumber() < g.getNumPlayers()) {
									server.continueTakingBets(g, threads.get(g.getNumber()), threads);
								}
								if (g.getNumber() == g.getNumPlayers()) { //ALL PLAYERS HAVE BETTED
									g.resetNumber();
									server.dealCards(g);
								}
							}
						}
					} //end of betOK
				} //end of requestBetMessage
				
				else if (obj instanceof DealtCardsMessage) {
					DealtCardsMessage dcm = (DealtCardsMessage) obj;	
					Vector<Game> games = server.getGames();
					//find the correct game
					for (Game g : games) {
						if (dcm.getGameName().equals(g.getGameName())) {
							server.beginRound(g, this, g.getServerThreads());
						}
					}
				}
				
				else if (obj instanceof RequestActionMessage) {
					RequestActionMessage ram = (RequestActionMessage) obj;
					Vector<Game> games = server.getGames();
					for (Game g : games) {
						if (ram.getGameName().equals(g.getGameName())) {
							String response = ram.getResponse();
							if (response.equals("stay") || response.equals("1")) {
								server.stay(g, this, ram.getUsername(), g.getServerThreads());
							}
							else if (response.equals("hit") || response.equals("2")) {
								server.hit(g, this, ram.getUsername(), g.getServerThreads());
							}
						}
					}
				} //end of ram catcher
				
				else if (obj instanceof FinalizeTurnMessage) {
					FinalizeTurnMessage ftm = (FinalizeTurnMessage) obj;
					Vector<Game> games = server.getGames();
					for (Game g : games) {
						//get the correct game
						if (g.getGameName().equals(ftm.getMessage())) {
														
							//send call for next thread
							Vector<ServerThread> threads = g.getServerThreads();
							//print the hand
							server.printHand(g, this, ftm.getUsername(), threads, ftm.getStatus());
							
							g.incrementNumber();
							if (g.getNumber() < g.getNumPlayers()) {
								server.beginRound(g, threads.get(g.getNumber()), threads);
							}
							
							//ALL PLAYERS HAVE FINALIZED
							if (g.getNumber() == g.getNumPlayers()) { 
								g.resetNumber();
								server.dealerTurn(g);

							}
						}
					}
				}
			}
		} catch (IOException ioe) {
			System.out.println("ioe in ServerThread.run(): " + ioe.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		}
		
	}
}
