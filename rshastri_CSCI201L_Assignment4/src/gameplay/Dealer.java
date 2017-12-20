package gameplay;

import java.util.Collections;
import java.util.Vector;

public class Dealer {
	

	private Vector<Card> deck;
	private Vector<Card> dealerHand;
	private Vector<Integer> potentialValues;
	private int finalHand;
	private Boolean isBusted;
	private Boolean isStaying;
	
	public Dealer() {
		//create new deck
		deck = new Vector<Card>();
		dealerHand = new Vector<Card>();
		potentialValues = new Vector<Integer>();
		
		String[] suits = {"SPADES", "HEARTS" , "DIAMONDS", "CLUBS"};
		String[] names = {"ACE", "TWO", "THREE", "FOUR", "FIVE" ,
		"SIX", "SEVEN", "EIGHT", "NINE", "TEN", "JACK", "QUEEN", "KING"};
		int[] values = {11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
		for (int s = 0; s < suits.length; s++) {
			for (int n = 0; n < names.length; n++) {
				deck.add(new Card(names[n], suits[s], values[n]));
			}
		}
		
		setFinalHand(0);
		setIsBusted(false);
		setIsStaying(false);
	}
	
	public void shuffle () {
		Collections.shuffle(deck);
	}
	
	public void deal() {
		
	}
	
	//only hit once
//	public void hit() {
//		//if dealer gets 18 or higher they CANNOT hit
//		if (dealerHand >= 18) {
//			isStaying = true;
//			return;
//		} else {
//			//dealer must hit
//			Card newCard = deck.get(0);
//			dealerHand += newCard.getValue();
//			deck.remove(0);
//			if (dealerHand > 21) {
//				//check if card is ace
//				if (newCard.getName().equals("ACE")) {
//					dealerHand -= 11;
//					dealerHand++;
//				}
//				isBusted = true;
//			} else if (dealerHand > 17 && dealerHand < 22) {
//				isStaying = true;
//			}
//		}	
//	}
	
	public Vector<Card> getDeck() {
		return deck;
	}

	public Vector<Card> getDealerHand() {
		return dealerHand;
	}

	public void setDealerHand(Card c1, Card c2) {
		dealerHand.add(c1);
		dealerHand.add(c2);
	}

	public Vector<Integer> getInitialHandValues(Vector<Card> hand) {
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

	public int getFinalHand() {
		return finalHand;
	}

	public void setFinalHand(int finalHand) {
		this.finalHand = finalHand;
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
