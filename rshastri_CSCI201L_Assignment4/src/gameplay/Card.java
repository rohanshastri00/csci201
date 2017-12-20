package gameplay;
public class Card {
	private String name;
	private int value;
	private String suit;
	
	public Card(String name_, String suit_, int value_) {
		name = name_;
		suit = suit_;
		value = value_;
	}
	
	public String getName() {
		return name;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getSuit() {
		return suit;
	}
}
