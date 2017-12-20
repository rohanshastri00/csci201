
package shastri;

import java.util.List;

public class Course {

    private String number;
    private String title;
    private String units;
    private String term;
    private String year;
    private List<StaffMember> staffMembers = null;
    private List<Meeting> meetings = null;
    private Syllabus syllabus;
    private Schedule schedule;
    private List<Assignment> assignments = null;
    private List<Exam> exams = null;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<StaffMember> getStaffMembers() {
        return staffMembers;
    }

    public void setStaffMembers(List<StaffMember> staffMembers) {
        this.staffMembers = staffMembers;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    public Syllabus getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(Syllabus syllabus) {
        this.syllabus = syllabus;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    	//returns the exact staffMember who has OH given the day and time passed in
    public StaffMember findCorrectOHTime(String day, String time) {
    	
		//iterate through all staff members
    		for (int i = 0;  i < getStaffMembers().size(); i++) {
    			if (getStaffMembers().get(i).getOfficeHours() != null) {
    				//if they have OH iterate through them
    		        int staffOH = getStaffMembers().get(i).getOfficeHours().size();
    		        	for (int j = 0; j < staffOH; j++) {
    		        		//if they have a given day
    		        		if (getStaffMembers().get(i).getOfficeHours().get(j).getDay().equals(day)) {
    		        			if (getStaffMembers().get(i).getOfficeHours().get(j).getTime().getStart().equals(time)) {
    		    	                return getStaffMembers().get(i);

    		        			}
	    		        	}
	    			}
	    		}
    			
	    }    		
		StaffMember temp = new StaffMember();
		Name tempName = new Name();
		tempName.setFname("No One");
		tempName.setLname("Available");
		temp.setName(tempName);
		temp.setImage("http://www-scf.usc.edu/~csci201/images/USC_seal.gif");	   
		return temp;
    }    
}
