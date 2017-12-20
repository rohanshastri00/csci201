package com.jsonparse;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
	public static boolean mainMenu (TopLevel school, boolean isRunning) {
		Scanner scan = new Scanner(System.in);
		do {
			//print out display schools, exit
			System.out.println("1) Display Schools");
			System.out.println("2) Exit");
			System.out.print("What would you like to do? ");
			int choice = 0;
		    while (!scan.hasNextInt()) {
				System.out.println("That is not a valid option.");
		    		scan.next();
		    }
             choice = scan.nextInt();
            
			try { 
				if (choice == 1) {
					displaySchools(school, isRunning);
					return isRunning;
				}
				if (choice == 2) {
					System.out.println("Thank you for using my program!");
					System.exit(1);
				}
				//check if greater than 2 or less than 1
				if (choice < 1 || choice > 2) {
					System.out.println("That is not a valid option.");
					continue;
				}
			}
			catch (NumberFormatException nfe) {
				System.out.println("That is not a valid option.");
				continue;
			}
		} while (isRunning);
		scan.close();
		return isRunning;
	} 
	
	public static boolean displaySchools (TopLevel school, boolean isRunning) {
		Scanner scan = new Scanner(System.in);
        do {
    			System.out.println("Schools");
        		//schoolListSize represents the length of the TopLevel array, i.e. # schools
            int schoolListSize = 0;
            //have to declare i outside for loop to use later
            int i;
            for(i = 0; i < school.getSchools().size(); i++) {
                System.out.println((i + 1) + ") " + school.getSchools().get(i).getName());
                schoolListSize++;
            }
            int mainMenuOption = i + 1;
            int exitOption = i+2;
            System.out.println(i+1 + ")" + " Go to main menu");
            System.out.println(i + 2 + ")" + " Exit");
			System.out.print("What would you like to do? ");
            try {
            int schoolChoice = 0;
    		    while (!scan.hasNextInt()) {
    				System.out.println("That is not a valid option.");
    		    		scan.next();
    		    }
    		    schoolChoice = scan.nextInt();
                
                if(schoolChoice > schoolListSize) {
                    if(schoolChoice == mainMenuOption) {
                        mainMenu(school, isRunning);
                        return isRunning;
                    }
                    if(schoolChoice == exitOption) {
    						System.out.println("Thank you for using my program!");
    						System.exit(1);
    						return false;
                    }
                }
                if(schoolChoice > exitOption || schoolChoice < 1) {
                    System.out.println("That is not a valid option.");
                    continue;
                }
                
                displayDepartments(school, isRunning, schoolChoice-1);
              
            }catch(NumberFormatException e) {
                System.out.println("That is not a valid option."); 
            }
            
        }while(isRunning);
        scan.close();
        return isRunning;
	}
	
	public static boolean displayDepartments(TopLevel school, boolean isRunning, int schoolChoice) {
		Scanner scan = new Scanner(System.in);
		do {
			System.out.println("Departments");
			int deptListSize = 0;
			int i = 0;
			List<Department> departments = school.getSchools().get(schoolChoice).getDepartments();
			for (i = 0; i < departments.size(); i++) {
				String courseName = departments.get(i).getLongName();
				String coursePrefix = departments.get(i).getPrefix();
				System.out.println((i + 1) + ") " + courseName + " (" + coursePrefix + ")");
				deptListSize++;
			}
			int mainMenuOption = i + 1;
			int exitOption = i + 2;
			System.out.println(mainMenuOption + ") Go to Schools menu");
			System.out.println(exitOption + ") Exit");
			System.out.print("What would you like to do? ");
			try {
	            int deptChoice = 0;
	    		    while (!scan.hasNextInt()) {
	    				System.out.println("That is not a valid option.");
	    		    		scan.next();
	    		    }
	    		    deptChoice = scan.nextInt();
				
				if (deptChoice < 1 || deptChoice > exitOption) {
					System.out.println("That is not a valid option.");
					continue;
				}
				//return to previous menu
				if (deptChoice == deptListSize + 1) {
					displaySchools(school, isRunning);
					return isRunning;
				}
				if (deptChoice == deptListSize + 2) {
					System.out.println("Thank you for using my program!");
					System.exit(1);
					return false;
				}
				displayCourses(school,isRunning, schoolChoice, deptChoice - 1);
			} catch (NumberFormatException e) {
				System.out.println("That is not a valid option.");
				continue;
			}

		} while (isRunning);
		scan.close();
		return isRunning;
	}
	
	public static boolean displayCourses(TopLevel school, boolean isRunning, int schoolChoice, int deptChoice) {

		Scanner scan = new Scanner(System.in);
		do {
			String courseMenuTitle = school.getSchools().get(schoolChoice).getDepartments().get(deptChoice).getPrefix();
			System.out.println(courseMenuTitle + " Courses");
			int courseListSize = 0;
			int i = 0;
			List<Course> courses = school.getSchools().get(schoolChoice).getDepartments().get(deptChoice).getCourses();
			for (i = 0; i < courses.size(); i++) {
				Course currentCourse = courses.get(i);
					String prefix = school.getSchools().get(schoolChoice).getDepartments().get(deptChoice).getPrefix();
					String courseNumber = currentCourse.getNumber();
					String term = currentCourse.getTerm();
					String year = currentCourse.getYear();
				System.out.println((i + 1) + ") " + prefix + " " + courseNumber + " " + term + " " + year);
				courseListSize++;
			}

			int mainMenuOption = courseListSize + 1;
			int exitOption = courseListSize + 2;
			System.out.println(mainMenuOption + ") Go to Departments menu");
			System.out.println(exitOption + ") Exit");
			System.out.print("What would you like to do? ");
			try {
	            int courseChoice = 0;
	    		    while (!scan.hasNextInt()) {
	    				System.out.println("That is not a valid option.");
	    		    		scan.next();
	    		    }
	    		    courseChoice = scan.nextInt();
				

				if (courseChoice < 1 || courseChoice > exitOption) {
					System.out.println("That is not a valid option.");
					continue;
				}
				//return to depts menu
				if (courseChoice == mainMenuOption) { 
					displayDepartments(school, isRunning, schoolChoice);
					return isRunning;
				}
				if (courseChoice == exitOption) {
					System.out.println("Thank you for using my program!");
					System.exit(1);
					return false;
				}
				displayCourseInfo(school, isRunning, schoolChoice, deptChoice, courseChoice - 1);
			} catch (NumberFormatException e) {
				System.out.println("That is not a valid option.");
				continue;
			}
		} while (isRunning);
		scan.close();
		return isRunning;
	}
	
	public static boolean displayCourseInfo(TopLevel school, boolean isRunning, int schoolChoice, int deptChoice, int courseChoice) {
		Scanner scan = new Scanner(System.in);
		
		do {
			String title = createTitle (school, schoolChoice, deptChoice, courseChoice);
			System.out.println(title);
			String prefix = school.getSchools().get(schoolChoice).getDepartments().get(deptChoice).getPrefix();
			System.out.println("1) View Course Staff");
			System.out.println("2) View Meeting Information");
			System.out.println("3) Go to " + prefix + " Courses menu");
			System.out.println("4) Exit");
			System.out.print("What would you like to do? ");
			
			try {				
	            int courseInfoChoice = 0;
	    		    while (!scan.hasNextInt()) {
	    				System.out.println("That is not a valid option.");
	    		    		scan.next();
	    		    }
	    		    courseInfoChoice = scan.nextInt();
						
				if (courseInfoChoice < 1 || courseInfoChoice > 4) {
					System.out.println("That is not a valid option.");
				}
				if (courseInfoChoice == 1) {
					courseStaffMenu(school, isRunning, schoolChoice, deptChoice, courseChoice, courseInfoChoice-1);
				}
				if (courseInfoChoice == 2) {
					courseMeetingMenu(school, isRunning, schoolChoice, deptChoice, courseChoice, courseInfoChoice-1);
				}
				if (courseInfoChoice == 3) {
					displayCourses(school, isRunning, schoolChoice, deptChoice);
					return isRunning;
				}
				if (courseInfoChoice == 4) {
					System.out.println("Thank you for using my program!");
					System.exit(1);
				}
			} catch (NumberFormatException e) {
				System.out.println("That is not a valid option.");
				continue;
			}

		} while (isRunning);
		scan.close();
		return isRunning;
	}
	
	public static boolean courseStaffMenu(TopLevel school, boolean isRunning, int schoolChoice, int deptChoice, int courseChoice, int courseInfoChoice) {
		Scanner scan = new Scanner(System.in);
		do {
			String title = createTitle (school, schoolChoice, deptChoice, courseChoice);
			System.out.println(title);
			System.out.println("Course Staff");
			System.out.println("1) View Instructors");
			System.out.println("2) View TAs");
			System.out.println("3) View CPs");
			System.out.println("4) View Graders");
			System.out.println("5) Go to " + title + " menu");
			System.out.println("6) Exit");
			System.out.print("What would you like to do? ");
			try {				
	            int courseStaffMenuChoice = 0;
	    		    while (!scan.hasNextInt()) {
	    				System.out.println("That is not a valid option.");
	    		    		scan.next();
	    		    }
	    		    courseStaffMenuChoice = scan.nextInt();
				
				if (courseStaffMenuChoice > 6) {
					System.out.println("That is not a valid option.");
				}
				if (courseStaffMenuChoice == 1) {
					displayInstructor(school, schoolChoice, deptChoice, courseChoice, courseInfoChoice, "Instructor");
				}
				if (courseStaffMenuChoice == 2) {
					displayInstructor(school, schoolChoice, deptChoice, courseChoice, courseInfoChoice,"TA");
				}
				if (courseStaffMenuChoice == 3) {
					displayInstructor(school, schoolChoice, deptChoice, courseChoice, courseInfoChoice,"CP");
				}
				if (courseStaffMenuChoice == 4) {
					displayInstructor(school, schoolChoice, deptChoice, courseChoice, courseInfoChoice,"Grader");

				}
				//go back to previous menu
				if (courseStaffMenuChoice == 5) {
					displayCourseInfo(school, isRunning, schoolChoice, deptChoice, courseChoice);
					return isRunning;
				}
				if (courseStaffMenuChoice == 6) {
					System.out.println("Thank you for using my program!");
					System.exit(1);
					return false;
				}
			} catch (NumberFormatException e) {
				System.out.println("That is not a valid option.");
			}
		} while (isRunning);
		scan.close();
		return isRunning;
	}

	public static boolean courseMeetingMenu(TopLevel school, boolean isRunning, int schoolChoice, int deptChoice, int courseChoice, int courseInfoChoice) {
		Scanner scan = new Scanner(System.in);
		do {
			String title = createTitle (school, schoolChoice, deptChoice, courseChoice);
			System.out.println(title);
			System.out.println("Meeting Information");
			System.out.println("1) Lecture");
			System.out.println("2) Lab");
			System.out.println("3) Quiz");
			System.out.println("4) Go to " + title + " menu");
			System.out.println("5) Exit");
			System.out.print("What would you like to do? ");

			try {
	            int courseMeetingMenuChoice = 0;
	    		    while (!scan.hasNextInt()) {
	    				System.out.println("That is not a valid option.");
	    		    		scan.next();
	    		    }
	    		    courseMeetingMenuChoice = scan.nextInt();
				
				if (courseMeetingMenuChoice == 1) {
					displayMeetingInfo(school, schoolChoice, deptChoice, courseChoice, courseInfoChoice, "Lecture");
				}
				if (courseMeetingMenuChoice == 2) {
					displayMeetingInfo(school, schoolChoice, deptChoice, courseChoice, courseInfoChoice, "Lab");
				}
				if (courseMeetingMenuChoice == 3) {
					displayMeetingInfo(school, schoolChoice, deptChoice, courseChoice, courseInfoChoice, "Quiz");
				}
				if (courseMeetingMenuChoice == 4) {
					displayCourseInfo(school, isRunning, schoolChoice, deptChoice, courseChoice);
					return true;
				}
				if (courseMeetingMenuChoice == 5) {
					System.out.println("Thank you for using my program!");
					System.exit(1);
					return false;
				}
			}
			catch (NumberFormatException e) {
				System.out.println("That is not a valid option.");
				continue;
			}
		} while (isRunning);
		scan.close();
		return isRunning;
	}
	
	public static void displayInstructor(TopLevel school, int schoolChoice, int deptChoice, int courseChoice, int courseInfoChoice, String category) {
		List<StaffMember> currentStaffMembers = school.getSchools().get(schoolChoice).getDepartments().get(deptChoice).getCourses().get(courseChoice).getStaffMembers();
		String title = createTitle (school, schoolChoice, deptChoice, courseChoice);
		System.out.println(title);
		for (int i = 0; i < currentStaffMembers.size(); i++) {
			//check if type of staff member matches the category
			if (currentStaffMembers.get(i).getType().equals(category.toLowerCase())) {
				System.out.println(category);
				System.out.println("Name: " + currentStaffMembers.get(i).getName().getFname() + " " + currentStaffMembers.get(i).getName().getLname());
				
				String email = currentStaffMembers.get(i).getEmail();
				if (email == null) { email = " "; }
				System.out.println("Email: " + email);
				
				String image = currentStaffMembers.get(i).getImage();
				if (image == null) { image = " "; }
				System.out.println("Image: " + image);
				
				String phone = currentStaffMembers.get(i).getPhone();
				if (phone == null) { phone = " "; }
				System.out.println("Phone:  " + phone);
				
				String office = currentStaffMembers.get(i).getOffice();
				if (office == null) { office = " "; }
				System.out.println("Office: " + office);
				
				List<OfficeHour> specificStaffHours = currentStaffMembers.get(i).getOfficeHours();
				System.out.print("Office Hours: ");
				for (int j = 0; j < specificStaffHours.size(); j++) {
					String currentDay = specificStaffHours.get(j).getDay();
					Time time = specificStaffHours.get(j).getTime();
					String startTime = "";
					String endTime = "";
					boolean hoursListed = false;
					if (time != null) {
						startTime = time.getStart();
						endTime = time.getEnd();
						hoursListed = true;
					}

					if (currentDay == null) { currentDay = " "; }
					if (hoursListed) { System.out.print(currentDay + " " + startTime + "-" + endTime); } 
					else { System.out.print(currentDay); }

					try {
						//check if there is more hours to list
						if (specificStaffHours.get(j + 1).getDay() != null) {
							System.out.print(", ");
						}
					} catch (IndexOutOfBoundsException e) {
						System.out.println("");
						System.out.println("");
						break;
					}
				}
			}
		}

	}

	public static void displayMeetingInfo(TopLevel school, int schoolChoice, int deptChoice,int courseChoice, int courseInfoChoice, String category) {

			List<Meeting> selectedMeeting = school.getSchools().get(schoolChoice).getDepartments().get(deptChoice).getCourses().get(courseChoice).getMeetings();
			String title = createTitle (school, schoolChoice, deptChoice, courseChoice);
			System.out.println(title);
			Meeting currentAssistants = new Meeting();
			for (int i = 0; i < selectedMeeting.size(); i++) {
				if (selectedMeeting.get(i).getType().equals(category.toLowerCase())) {
					currentAssistants = selectedMeeting.get(i);
					System.out.println(category + " Meeting Information");
					
					String section = selectedMeeting.get(i).getSection();
					if (section == null) { section = " "; }
					System.out.println("Section : " + section);
					
					String room = selectedMeeting.get(i).getRoom();
					if (room == null) { room = " "; }
					System.out.println("Room: " + room);
					
					List<MeetingPeriod> selectedMeetingPeriods = selectedMeeting.get(i).getMeetingPeriods();
					System.out.print("Meetings: ");
					for (int j = 0; j < selectedMeetingPeriods.size(); j++) {
						
						String day = selectedMeetingPeriods.get(j).getDay();
						if (day == null) { day = " "; } 
						
						Time time = selectedMeetingPeriods.get(j).getTime();
						String startTime = "";
						String endTime = "";
						boolean timeListed = false;
						if (time != null) {
							startTime = time.getStart();
							endTime = time.getEnd();
							timeListed = true;
						}
						if(timeListed) { System.out.print(day + " " + startTime + " " + endTime); }
						else { System.out.print(day); }
						
						
						try {
							if(selectedMeetingPeriods.get(j+1).getDay() != null) {
								System.out.print(", ");
							}
						} catch(IndexOutOfBoundsException e) {
							System.out.println("");
							break;
						}
					}
					//two lists, one to pull all assistants another to get the specific ones for the meeting
					ArrayList<StaffMember> courseStaff = new ArrayList<StaffMember>();
					ArrayList<StaffMember> specificStaff = new ArrayList<StaffMember>();

					//find the course
					Course currentCourse = school.getSchools().get(schoolChoice).getDepartments().get(deptChoice).getCourses().get(courseChoice);
					//fill courseStaff with all staff members of course
					for(int k = 0; k < currentCourse.getStaffMembers().size(); k++) courseStaff.add(currentCourse.getStaffMembers().get(k));
				
					try {
						//for every staff in courseStaff
						for(int i2 = 0; i2 < courseStaff.size(); i2++) {
							//find all the assistants in the specific meeting
							for(int j2 = 0; j2 < currentAssistants.getAssistants().size(); j2++) {
								//if the ID of the staff matches w the specific assistant IDs
								if(courseStaff.get(i2).getId().equals(currentAssistants.getAssistants().get(j2).getStaffMemberID())) {
									//add them to the specific staff
									specificStaff.add(courseStaff.get(i2));
								}
							}
						}
						//now print out assistants
						System.out.print("Assistants: ");
						for(int k2 = 0; k2 < specificStaff.size(); k2++) {
							System.out.print(specificStaff.get(k2).getName().getFname() + " " + specificStaff.get(k2).getName().getLname());
							if(k2 != specificStaff.size()-1) {
								System.out.print(", ");
							}
						}
					} catch(NullPointerException e) {
						System.out.println("Assistants: None");
					}
					System.out.println();
					System.out.println();
				}
			}//end of for loop
	}

	public static String createTitle (TopLevel school, int schoolChoice, int deptChoice, int courseChoice) {
		Course currentCourse = school.getSchools().get(schoolChoice).getDepartments().get(deptChoice).getCourses().get(courseChoice);
			String prefix = school.getSchools().get(schoolChoice).getDepartments().get(deptChoice).getPrefix();
			String courseNumber = currentCourse.getNumber();
			String term = currentCourse.getTerm();
			String year = currentCourse.getYear();
			String title = prefix + " " + courseNumber + " " + term + " " + year;
			return title;
	}
	
}
