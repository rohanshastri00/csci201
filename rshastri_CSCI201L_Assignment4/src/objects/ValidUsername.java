package objects;

import java.io.Serializable;

public class ValidUsername implements Serializable {

	private static final long serialVersionUID = 1L;
	private int state;
	//0 - still waiting for response
	//1 - got a response, correct (username was added)
	//2 - got a response, false (username already existed)
	//-1 - gameName wasn't found, try for new input
	public ValidUsername() {
		state = 0;
	}
	
	public ValidUsername(int result) {
		if (result == 1) {
			state = 1;
		} else if (result == 0){
			state = 2;
		} else {
			state = -1;
		}
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int newState) {
		state = newState;
	}
}
