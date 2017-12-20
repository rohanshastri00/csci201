package messages;

public class RequestBetMessage extends Message {


	private static final long serialVersionUID = 1L;
	private int betAmount;
	private String username;
	private String gameName;
	private Boolean validBet;
	public RequestBetMessage(String message_, String gameName_, int betAmount_, String username_) {
		super(message_);
		gameName = gameName_;
		betAmount = betAmount_;
		username = username_;
	}
	public int getBetAmount() {
		return betAmount;
	}
	public void setBetAmount(int betAmount_) {
		betAmount = betAmount_;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public Boolean getValidBet() {
		return validBet;
	}
	public void setValidBet(Boolean validBet) {
		this.validBet = validBet;
	}
	

}
