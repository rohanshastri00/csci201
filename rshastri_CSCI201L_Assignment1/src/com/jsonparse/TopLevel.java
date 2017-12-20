package com.jsonparse;
import java.util.ArrayList;

public class TopLevel {
	public ArrayList<School> getSchools() {
		return schools;
	}

	public void setSchools(ArrayList<School> schools) {
		this.schools = schools;
	}

	private ArrayList<School> schools;
}