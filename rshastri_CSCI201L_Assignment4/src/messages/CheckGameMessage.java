package messages;

public class CheckGameMessage extends Message {
	
	private static final long serialVersionUID = 1L;
	private int numPlayers;

	public CheckGameMessage(String gameName, int numPlayers_) {
		super(gameName);
		numPlayers = numPlayers_;
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}
}
