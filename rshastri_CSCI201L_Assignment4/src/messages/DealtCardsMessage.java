package messages;


public class DealtCardsMessage extends Message {
	
	private static final long serialVersionUID = 1L;
	private String gameName;
	public DealtCardsMessage(String dealtCardUI_, String gameName_) {
		super(dealtCardUI_);
		setGameName(gameName_);
	}


	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	
}
