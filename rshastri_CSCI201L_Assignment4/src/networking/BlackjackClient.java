package networking;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

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

public class BlackjackClient extends Thread {
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private static ValidGameName vgn;
	private static ValidUsername vun;
	private static JoinGameMessage jgm;
	private static Boolean startGame;
	private Boolean gameStarter;
	private Scanner gameScan = null;
	
	public BlackjackClient(String hostname, int port) {
		try {
			System.out.println("Trying to connect to " + hostname + ":" + port);
			InetAddress address = InetAddress.getByName(hostname);
			Socket s = new Socket(address, port);
			System.out.println("Connected to " + address + ":" + port);
			ois = new ObjectInputStream(s.getInputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe in BlackjackClient constructor: " + ioe.getMessage());
		}
		
		vgn = new ValidGameName(); //state is 0 -- waiting
		vun = new ValidUsername(); //state is 0 -- waiting
		//if the client is the one starting the game, a gameStarter object should be created
		jgm = new JoinGameMessage("","");
		startGame = false;
		gameStarter = false;
		gameScan = new Scanner(System.in);

	}
	

	private Object getObjectOutputStream() {
		// need for check
		return oos;
	}

	private Object getObjectInputStream() {
		// need for check
		return ois;
	}
	
	public void run() {
		try {
			while(true) {
				//GETTING VALUE BACK FROM SERVERTHREAD
				Object obj = ois.readObject();
				//game name check - start game
				if (obj instanceof ValidGameName) {
					vgn = (ValidGameName) obj;
				} 
				//username check- start game
				else if (obj instanceof ValidUsername) {
					 vun = (ValidUsername) obj;
				} 
				else if (obj instanceof JoinGameMessage) {
					jgm = (JoinGameMessage) obj;	
					if (gameStarter) {
						
						if (jgm.getNumPlayersRemaining() == 1) {
							System.out.println(jgm.getRecentUser() + " joined the game");
							System.out.println("Waiting for " + jgm.getNumPlayersRemaining() + " other player to join...");
						}
						else if (jgm.getNumPlayersRemaining() > 1 ) {
							System.out.println(jgm.getRecentUser() + " joined the game");
							System.out.println("Waiting for " + jgm.getNumPlayersRemaining() + " other players to join...");
						}
		
						else if (jgm.getNumPlayersRemaining() == 0){
							System.out.println(jgm.getRecentUser() + " joined the game");
							System.out.println("Let the game commence. Good luck to all players!");
							startGame = true;
							sendMessage(new StartGameMessage(jgm.getMessage()));
						}
					}
					else if (jgm.allPlayersJoined()) {
						if(!gameStarter) {
							System.out.println("Let the game commence. Good luck to all players!");
						}
					}
				}
				
				
				//asking for bets now
				else if (obj instanceof RequestBetMessage) {
					RequestBetMessage rbm = (RequestBetMessage) obj;
					//print out status message
					System.out.println(rbm.getMessage());
					int betAmount = 0;
					if (rbm.getBetAmount() == 0) {
						String buffer = gameScan.nextLine();
						betAmount = Integer.parseInt(buffer);
						rbm.setBetAmount(betAmount);
					}
					sendMessage(rbm);
				}
				
				else if (obj instanceof DealtCardsMessage) {
					DealtCardsMessage dcm = (DealtCardsMessage) obj;	
					System.out.println(dcm.getMessage());
					if (gameStarter) {
						sendMessage(dcm);
					}
				}
				
				else if (obj instanceof RequestActionMessage) {
					RequestActionMessage ram = (RequestActionMessage) obj;
					//print out message that it's players turn
					
					System.out.println(ram.getMessage());
					if (ram.getResponse().equals("")) {
						gameScan.nextLine();
						String response = gameScan.nextLine();
						
						ram.setResponse(response);						
						sendMessage(ram);
					}
					
					
				}
				
				else if (obj instanceof FinalizeTurnMessage) {
					FinalizeTurnMessage ftm = (FinalizeTurnMessage) obj;
					sendMessage(ftm);
				}
				
				else if (obj instanceof Message) {
					//dealer is shuffling cards
					Message m = (Message) obj;
					System.out.println(m.getMessage());
				}
			}
			
		} catch (IOException ioe) {
			System.out.println("ioe in ChatClient.run(): " + ioe.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("cnfe: " + cnfe.getMessage());
		}
	}
	
	public void sendMessage (Message message) {
		try {
			oos.writeObject(message);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe in BlackjackClient sendMessage: " + ioe.getMessage());
		}
	}
	
	//checking to see if ip address and port are valid
	public static void main(String [] args) {
		System.out.println("Welcome to the Black Jack Server!");
		boolean isValidPort = false;
		boolean isValidAddress = false; 
		Scanner scan = new Scanner(System.in);
		String ipAddress = "";
		int port = 0; 
		int failedConnect = 0;
		BlackjackClient bjc = null;
		while (!isValidPort && !isValidAddress) {
			System.out.println("Please enter the ipaddress");
			if (failedConnect == 1) {
				scan.nextLine();
			}
			ipAddress  = scan.nextLine();
			System.out.println("Please enter the port");
			try {
				String buffer = scan.nextLine();
				port = Integer.parseInt(buffer);
			} catch (InputMismatchException ime){ }
			bjc = new BlackjackClient(ipAddress, port);	
			if (bjc.getObjectInputStream() != null && bjc.getObjectOutputStream() != null) {
				System.out.println("Client successfully created.");
				isValidPort = true;
				isValidAddress = true;
			} else {
				failedConnect = 1;
			}
			
		}
		
		int firstOption = 0;
		String gameName = "";
		String username = "";
		Boolean validGameName = false;
		Boolean validUsername = false;
		
		while (firstOption < 1 || firstOption > 2) {
			System.out.println("Please choose from the options below");
			System.out.println("1) Start Game");
			System.out.println("2) Join Game");
			startGame = false;

			String buffer = scan.nextLine();
			firstOption = Integer.parseInt(buffer);

			if (firstOption == 1) { //startGame
				int playerEntry = 0;
				System.out.println("Please choose the number of players in the game (1-3)");
				String buff = scan.nextLine();
				playerEntry = Integer.parseInt(buff);
				
				//checking for num players
				if (playerEntry < 1 || playerEntry > 3) {
					System.out.println("Invalid entry. Please choose the number of players in the game (1-3)");
					continue;
				} 
				//valid num players
				while (!validGameName) { //check if game name is valid
					
					System.out.println("Please choose a name for your game.");
					while (gameName.equals("")) {
						gameName = scan.nextLine();
					}
					bjc.sendMessage(new CheckGameMessage(gameName, playerEntry));
					//wait for object to get back and state to be changed
					while (vgn.getState() == 0) { }
					if (vgn.getState() == 1) {
						validGameName = true;
						bjc.gameStarter = true;
					}
					
					if (!validGameName) { //game name exists
						System.out.println("Invalid choice. This game has already been chosen by another user");
						gameName = "";
						vgn.setState(0);
					} 

				} 
				
				//valid game name
				while (!validUsername && vun.getState() != -1) {
					System.out.println("Please choose a username");
					while (username.equals("")) {
						scan.nextLine();
						scan.nextLine();
						scan.nextLine();
						username = scan.nextLine();
					}
					bjc.sendMessage(new CheckUsernameMessage(gameName, username));
					
					while (vun.getState() == 0) { }
					if (vun.getState() == 1) { //username didnt exist, now added
						validUsername = true;
					}
					if (!validUsername) {
						System.out.println("Invalid choice. This username has already been chosen by another player in this game");
						username = "";
						vun.setState(0);
					}
				}

				if(playerEntry == 1) { //Start game
					System.out.println("Let the game commence. Good luck to all the players!");
					startGame = true;
					bjc.sendMessage(new StartGameMessage(gameName));
				} else if(playerEntry == 2){
					System.out.println("Waiting for 1 other player to join...");
				} else if (playerEntry == 3) {
					System.out.println("Waiting for 2 other players to join...");
				}
				
			} //if option == 1
			
			if (firstOption == 2) { //join game
				while (!validGameName) {
					System.out.println("Please enter the name of the game you wish to join.");
					while (gameName.equals("")) {
						gameName = scan.nextLine();
					}
					bjc.sendMessage(new CheckGameMessage(gameName, 0));
					while (vgn.getState() == 0) { }
					if (vgn.getState() == 2) { //game exists
						validGameName = true;
					}
					if (!validGameName) { //game name does not exist
						System.out.println("Invalid choice. There are no ongoing games with this name");
						gameName = "";
						vgn.setState(0);
					} 	
				}
				
				//valid name
				while (!validUsername) {
					System.out.println("Please choose a username");
					while (username.equals("")) {
						username = scan.nextLine();
					}
					bjc.sendMessage(new CheckUsernameMessage(gameName, username));
					while (vun.getState() == 0) { } //need to adjust state of validUsername obj
					if (vun.getState() == 1) { //username now added
						validUsername = true;
					}
					if (!validUsername) {
						System.out.println("Invalid choice. This username has already been chosen by another player in this game");
						username = "";
						vun.setState(0);
					} 
				}
				//game exists, username valid, so add player to game
				bjc.sendMessage(new JoinGameMessage(gameName, username));
				System.out.println("The game will start shortly. Waiting for other players to join...");		
			}//end of join game (option == 2)
		} //end of option choices
		
	}
}
