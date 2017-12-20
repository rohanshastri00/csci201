package messages;

public class JoinGameMessage extends Message {

	private static final long serialVersionUID = 1L;
	private String recentUser;
	private int numPlayersRemaining;
	private Boolean allPlayers;
	private int state; 
	
	public JoinGameMessage(String gameName, String recentUser_) {
		super(gameName);
		recentUser = recentUser_;
		setNumPlayersRemaining(numPlayersRemaining);
		allPlayers = false;
		state = -1;
	}
	public void setNumPlayersRemaining(int numPlayersRemaining_) {
		numPlayersRemaining = numPlayersRemaining_;
	}
	
	public int getNumPlayersRemaining() {
		return numPlayersRemaining;
	}
	
	public String getRecentUser() {
		return recentUser;
	}
	
	public Boolean allPlayersJoined() {
		return allPlayers;
	}
	
	public void setAllPlayersJoined(Boolean value) {
		allPlayers = value;
	}
	
	public void checkAllPlayers(Boolean allPlayers_) {
		allPlayers = allPlayers_;
	}
}
