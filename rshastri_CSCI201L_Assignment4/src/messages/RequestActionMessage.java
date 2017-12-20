package messages;


public class RequestActionMessage extends Message {


	private static final long serialVersionUID = 1L;
	private String response;
	private String username;
	private String gameName;
	
	public RequestActionMessage(String status, String gameName, String response_, String username) {
		super(status);
		setGameName(gameName);
		response = response_;
		setUsername(username);
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
