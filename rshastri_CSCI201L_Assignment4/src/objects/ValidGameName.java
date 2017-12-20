package objects;

import java.io.Serializable;

public class ValidGameName implements Serializable {

	private static final long serialVersionUID = 1L;
	private int state;
	//0 - still waiting for response
	//1 - got a response, correct
	//2 - got a response, false
	//-1 - got a response, 
	public ValidGameName() {
		state = 0;
	}
	
	public ValidGameName(int result) {
		if (result == 1) {
			state = 1; //game does not exist
		} else if (result == 0){
			state = 2; //game exists
		} else if (result == 2){
			state = -1; //game does not exist so not added (Joining)
		}
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int newState) {
		state = newState;
	}
}
