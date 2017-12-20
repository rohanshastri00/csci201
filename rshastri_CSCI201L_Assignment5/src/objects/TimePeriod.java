package objects;

public class TimePeriod {
	private String day;
	private Time time;
	private String section;
	private Integer staffMemberID;
	
	public Integer getStaffMemberID() {
		return staffMemberID;
	}
	public String getDay() {
		return day;
	}

	public Time getTime() {
		return time;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public void setStaffMemberID(int staffMemberID) {
		this.staffMemberID = staffMemberID;
	}
}
