package objects;

public class StaffMemberID {
	private Integer staffMemberID;
	private String section;
	
	public String getSection() {
		return section;
	}
	public Integer getID() {
		return staffMemberID;
	}
	
	public void setID(Integer staffMemberID) {
		this.staffMemberID = staffMemberID;
	}

	public void setSection(String section) {
		this.section = section;;
	}
}
