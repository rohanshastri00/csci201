package objects;

import java.io.Serializable;

public class PlayersJoining implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String username;
	private Boolean canGameStart;
	public PlayersJoining() {
		username = "";
		setCanGameStart(false);
	}
	
	public PlayersJoining(String username_) {
		username = username_;
		setCanGameStart(false);
	}
	
	public String getUsername() {
		return username;
	}

	public Boolean getCanGameStart() {
		return canGameStart;
	}

	public void setCanGameStart(Boolean canGameStart) {
		this.canGameStart = canGameStart;
	}
	
}
