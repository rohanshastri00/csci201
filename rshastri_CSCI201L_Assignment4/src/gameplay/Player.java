package gameplay;
import java.util.Vector;


public class Player {
	private String username;
	private int chipTotal;
	private int betAmount;
	private int gameStatus;
	private String gameName;
	private Vector<Card> hand;
	Vector<Integer> potentialValues;
	private int highest;
	private Boolean isBusted;
	private Boolean isStaying;

	
	public Player (String username_, String gameName_) {
		username = username_;
		chipTotal = 500;
		gameStatus = 0; //figure out what this shit means
		gameName = gameName_;
		hand = new Vector<Card>();
		potentialValues = new Vector<Integer>();
		highest = 0;
		setIsBusted(false);
		setIsStaying(false);
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getChipTotal() {
		return chipTotal;
	}
	
	public void setChipTotal(int value) {
		chipTotal = value;
	}
	
	public int getBetAmount() {
		return betAmount;
	}
	
	public int getGameStatus() {
		return gameStatus;
	}
	
	public Vector<Card> getHand() {
		return hand;
	}
	
	public void setHand(Card c1, Card c2) {
		hand.add(c1);
		hand.add(c2);
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public Vector<Integer> getInitialHandValues() {
		int value1 = 0;
		int value2 = 0;
		for (int i = 0; i < hand.size(); i++) {
			//get current card
			Card temp = hand.get(i);
			//check if it's an ace
			if (temp.getName().equals("ACE")) {//got an ace
				value1 += 1;
				value2 += 11;
			} 
			else { //not an ace
				value1 += temp.getValue();
				value2 += temp.getValue();	
			}
		}
		//you gotta add value1 bc that's for sure valid
		potentialValues.add(value1);
		//value2 would not equal 0 if there's an ace
		if ((value2 - value1) != 0) {
			if (value2 == 22) {
				value2 = 12;
			}
			potentialValues.addElement(value2);
		}
		return potentialValues;
	}

	public void setBetAmount(int betAmount_) {
		betAmount = betAmount_;
	}

	public Vector<Integer> calculateUpdatedValue(Card c1) {
		int temp = 0;
		int temp2 = 0;
		for (int i = 0; i < potentialValues.size();i++) {
				temp = potentialValues.get(i);
				
			if (c1.getName().equals("ACE")) {
				temp += 1;
				if (potentialValues.size() > 1) { //size is 2 (already got an ace)
					temp2 = potentialValues.get(i+1);
					temp2 += 11;
				}
				else { //size is 1
					temp2 = potentialValues.get(i);
					temp2 += 11;
				}
				break;
			} 
			else { //not an ace
				temp += c1.getValue();
			}
		}
		
		potentialValues.set(0, temp);
		
		if ((temp2 != 0) && ((temp2 - temp) != 0)) {//difference so there was an ace
			if (potentialValues.size() > 1) {
				potentialValues.set(1, temp2);
			}
			else {
				potentialValues.add(temp2);
			}
		}
		
		return potentialValues;
	}

	public void setFinalHand(int highest_) {

		highest = highest_;
	}
	
	public int getFinalHand() {
		return highest;
	}

	public Boolean getIsBusted() {
		return isBusted;
	}

	public void setIsBusted(Boolean isBusted) {
		this.isBusted = isBusted;
	}

	public Boolean getIsStaying() {
		return isStaying;
	}

	public void setIsStaying(Boolean isStaying) {
		this.isStaying = isStaying;
	}
	
	

}
