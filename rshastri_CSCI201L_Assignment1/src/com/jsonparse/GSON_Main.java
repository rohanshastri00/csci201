package com.jsonparse;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileReader;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.jsonparse.TopLevel;
import com.jsonparse.Course;
import com.jsonparse.Department;
import com.jsonparse.Meeting;
import com.jsonparse.StaffMember;


public class GSON_Main {
	static boolean goodFile = true;
	public static void main(String[] args) {
		do {
			TopLevel school = new TopLevel();
			Scanner scan = new Scanner(System.in);
			try {
				System.out.print("What is the name of the input file? ");
				String filename = scan.next();
				
				Gson parse = new Gson();
					BufferedReader br = new BufferedReader(new FileReader(filename));
					school = parse.fromJson(br,TopLevel.class);
			} catch(FileNotFoundException e) {
				System.out.println("That file could not be found.");
				continue;
			} catch (JsonParseException e) {
				System.out.println("That file is not a well-formed JSON file.");
				continue;	
			}

			boolean initialCheck = false;
			boolean validSchool = false;
			//assume true unless the checks catch something otherwise
			boolean nullCheck = true;
			boolean validDepts = true;
			boolean validCourses = true;
			boolean validStaff = true;
			boolean validMeeting = true;
			int numSchools = school.getSchools().size();
			try {
				//initial check
				if (numSchools >= 1) validSchool = true;
				
				if (validSchool) {
					for (int i = 0; i < numSchools; i++) {
						if (school.getSchools().get(i).getDepartments().size() < 1) {
							validDepts = false;
						}
					}
				}
				
				if (validSchool && validDepts) {
					for (int i = 0; i < numSchools; i++) {
						for (int j = 0; j < school.getSchools().get(i).getDepartments().size(); j++) {
							if (school.getSchools().get(i).getDepartments().get(j).getCourses().size() < 1) {
								validCourses = false;
							}
						}
					}
				}
				
				if (validSchool && validDepts && validCourses) {
					for (int i = 0; i < numSchools; i++) {
						for (int j = 0; j < school.getSchools().get(i).getDepartments().size(); j++) {
							for (int k = 0; k < school.getSchools().get(i).getDepartments().get(j).getCourses().size(); k++) {
								if (school.getSchools().get(i).getDepartments().get(j).getCourses().get(k).getStaffMembers().size() < 1) {
									validStaff = false;
								}
								if (school.getSchools().get(i).getDepartments().get(j).getCourses().get(k).getMeetings().size() < 1) {
									validMeeting = false;
								}
							}
						}
					}
				}
				if (validSchool && validDepts && validCourses && validStaff && validMeeting) {
					initialCheck = true;
				}
				
				//NULL CHECK
				
				ArrayList<School> schools = school.getSchools();
				if (numSchools > 0) {
					for (int i = 0; i < schools.size(); i++) {
						if (schools.get(i).getName() == null) {
							nullCheck = false;
						}
						List<Department> departments = schools.get(i).getDepartments();
						if (departments.size() > 0) {
							for (int j = 0; j < departments.size(); j++) {
								if (departments.get(j).getPrefix() == null || departments.get(j).getLongName() == null) {
									nullCheck = false;
								}
								List<Course> courses = departments.get(j).getCourses();
								if (courses.size() > 0) {
									for (int k = 0; k < courses.size(); k++) {
										if (courses.get(k).getNumber() == null) nullCheck = false; 
										if (courses.get(k).getTerm() == null) nullCheck = false;
										if (courses.get(k).getYear() == null) nullCheck = false;
										
										List<Meeting> meetings = courses.get(k).getMeetings();
										List<StaffMember> staff = courses.get(k).getStaffMembers();
										
										if (meetings.size() > 0 && staff.size() > 0) {
											for (int m = 0; m < meetings.size(); m++) {
												if (meetings.get(m).getType() == null) nullCheck = false;
												if (meetings.get(m).getSection() == null) nullCheck = false;
											}
											for (int s = 0; s < staff.size(); s++) {
												if (staff.get(s).getType() == null) nullCheck = false; 
												if (staff.get(s).getId() == null) nullCheck = false; 
												if (staff.get(s).getName() == null) nullCheck = false;
											}
										}
									}
								}
							}
						}
					}
				}//end of null check
				
			} catch (NullPointerException e) {
				System.out.println("That file could not be found.");
				System.exit(1);
			} catch (JsonParseException e) {
				System.out.println("That file is not a well-formed JSON file.");
				System.exit(1);
			}
			boolean isRunning = true;
			if (initialCheck && nullCheck) {
				while (isRunning) {
					isRunning = Menu.mainMenu(school, isRunning);
				}
				goodFile = false;
				scan.close();

			}
		} while (goodFile);
	} 
}
