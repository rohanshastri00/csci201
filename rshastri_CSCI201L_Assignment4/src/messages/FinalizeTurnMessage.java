package messages;

public class FinalizeTurnMessage extends Message {

	
	private static final long serialVersionUID = 1L;
	private String username;
	private String status;
	public FinalizeTurnMessage(String gameName, String username_, String status_) {
		super(gameName);
		setUsername(username_);
		setStatus(status_);
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
