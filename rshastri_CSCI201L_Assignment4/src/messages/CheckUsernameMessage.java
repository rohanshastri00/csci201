package messages;

public class CheckUsernameMessage extends Message {
	
	private static final long serialVersionUID = 1L;
	private String username;
	
	public CheckUsernameMessage(String gameName, String username_) {
		super(gameName);
		username = username_;
	}
	
	public String getUsername() {
		return username;
	}

}
