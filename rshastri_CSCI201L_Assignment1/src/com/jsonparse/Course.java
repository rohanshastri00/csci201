package com.jsonparse;

import java.util.List;

public class Course {


	private String number;
	private String term;
	private String year;
	private List<StaffMember> staffMembers = null;
	private List<Meeting> meetings = null;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

}