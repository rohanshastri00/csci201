package objects;

public class Name {
	private String fname;
	private String lname;

	public String getFirstName() {
		return fname;
	}

	public String getLastName() {
		return lname;
	}
	
	public String getFullName() {
		return fname + " " + lname;
	}
}
