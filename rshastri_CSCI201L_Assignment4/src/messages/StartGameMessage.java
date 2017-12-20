package messages;

public class StartGameMessage extends Message {

	private static final long serialVersionUID = 1L;
	private String gameName;
	
	public StartGameMessage(String gameName_) {
		super(gameName_);
	}
	public String getGameName() {
		return gameName;
	}
}
