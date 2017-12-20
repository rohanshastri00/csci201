package servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import objects.Assignment;
import objects.Course;
import objects.DataContainer;
import objects.Deliverable;
import objects.Exam;
import objects.File;
import objects.Lab;
import objects.Lecture;
import objects.Meeting;
import objects.Program;
import objects.School;
import objects.StaffMember;
import objects.StaffMemberID;
import objects.Test;
import objects.Textbook;
import objects.TimePeriod;
import objects.Topic;
import objects.Week;

public class JSONPopulate {
	
	public void populate(String connection, DataContainer data) {
		
		if (isPopulated(connection)) {
			return;
		}
		
		School school = data.getSchools().get(0);
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		//insert into schools
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into schools");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();
			//check if the user already exists
			ps = conn.prepareStatement("INSERT INTO Schools (schoolName, imageLink) VALUES (?, ?);");
			ps.setString(1, school.getName()); // set first variable in prepared statement
			ps.setString(2, school.getImage());
			ps.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop
		
		
		//insert into departments
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into departments");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();
			//check if the user already exists
			ps = conn.prepareStatement("INSERT INTO Departments (deptName, prefix, schoolName) VALUES (?, ?, ?);");
			ps.setString(1, school.getDepartments().get(0).getLongName()); // set first variable in prepared statement
			ps.setString(2, school.getDepartments().get(0).getPrefix());
			ps.setString(3, school.getName());
			ps.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop
		
		
		//insert into courses
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into courses");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();
			//check if the user already exists
			
			ps = conn.prepareStatement("INSERT INTO Courses (courseNumber, courseName, term, courseYear, units, syllabus, deptName) VALUES (?, ?, ?, ?, ?, ?, ?);");
			Course course = school.getDepartments().get(0).getCourses().get(0);
			ps.setString(1, course.getNumber()); // set first variable in prepared statement
			ps.setString(2, course.getTitle());
			ps.setString(3, course.getTerm());
			ps.setInt(4, course.getYear());
			ps.setInt(5, course.getUnits());
			ps.setString(6, course.getSyllabus().getUrl());
			ps.setString(7, school.getDepartments().get(0).getLongName());
			ps.executeUpdate();
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop
		
		
		//insert into meetings
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into meetings");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();
			//check if the user already exists
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<Meeting> meetings = course.getMeetings();
			for (Meeting m : meetings) {
				ps = conn.prepareStatement("INSERT INTO Meetings (section, meetingType, room, courseNumber) VALUES (?, ?, ?, ?);");
				ps.setString(1, m.getSection());
				ps.setString(2, m.getMeetingType());
				ps.setString(3, m.getRoom());
				ps.setString(4, course.getNumber()); 
				ps.executeUpdate();
			}
			
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop
		
		
		//insert into meeting periods
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into meeting periods");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();
			//check if the user already exists
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<Meeting> meetings = course.getMeetings();
			for (Meeting m : meetings) {
				List<TimePeriod> meetingPeriods = m.getMeetingPeriods();
				for (TimePeriod tp : meetingPeriods) {
					ps = conn.prepareStatement("INSERT INTO MeetingPeriods (meetingDay, meetingStart, meetingEnd, section) VALUES (?, ?, ?, ?);");
					ps.setString(1, tp.getDay());
					ps.setString(2, tp.getTime().getStartTime());
					ps.setString(3, tp.getTime().getEndTime());
					ps.setString(4, m.getSection());
					ps.executeUpdate();
				}
			}
			
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop
		

		//insert into assistants (meetings)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into assistants (meetings)");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();
			//check if the user already exists
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<Meeting> meetings = course.getMeetings();
			for (Meeting m : meetings) {
				List<StaffMemberID> assistants = m.getAssistants();
				for (StaffMemberID sm : assistants) {
					ps = conn.prepareStatement("INSERT INTO Assistants (staffMemberID, section) VALUES (?, ?);");
					ps.setInt(1, sm.getID());
					ps.setString(2, m.getSection());
					ps.executeUpdate();
				}
			}
			
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop		
		
		
		//insert into staffMembers(courses)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into staffMembers(courses)");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();
			//check if the user already exists
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<StaffMember> staffMembers = course.getStaffMembers();
			for (StaffMember mem : staffMembers) {
				ps = conn.prepareStatement("INSERT INTO StaffMembers (staffMemberID, staffType, firstName, lastName, email, image, phone, office, courseNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
				ps.setInt(1, mem.getID());
				ps.setString(2, mem.getJobType());
				ps.setString(3, mem.getName().getFirstName());
				ps.setString(4, mem.getName().getFirstName());
				ps.setString(5, mem.getEmail());
				ps.setString(6, mem.getImage());
				ps.setString(7, mem.getPhone());
				ps.setString(8, mem.getOffice());
				ps.setString(9, course.getNumber());

				ps.executeUpdate();
			}
			
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop		
		
		//insert into officeHours(StaffMembers)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into officeHours(StaffMembers)");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();
			//check if the user already exists
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<StaffMember> staffMembers = course.getStaffMembers();
			for (StaffMember mem : staffMembers) {
				List<TimePeriod> officeHours = mem.getOH();
				for (TimePeriod tp : officeHours) {
					ps = conn.prepareStatement("INSERT INTO OfficeHours (staffMemberID, startTime, endTime, OHday, firstName, lastName) VALUES (?, ?, ?, ?, ?, ?);");
					ps.setInt(1, mem.getID());
					ps.setString(2, tp.getTime().getStartTime());
					ps.setString(3, tp.getTime().getEndTime());
					ps.setString(4, tp.getDay());
					ps.setString(5, mem.getName().getFirstName());
					ps.setString(6, mem.getName().getFirstName());
					ps.executeUpdate();
				}
			}
			
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop			
		

		//insert textbook (course)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert textbook (course)");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();			
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<Textbook>textbooks = course.getSchedule().getTextbooks();
			for (Textbook t : textbooks) {
				ps = conn.prepareStatement("INSERT INTO Textbooks (textBookNum, author, title, publisher, bookYear, isbn, courseNumber) VALUES (?, ?, ?, ?, ?, ?, ?);");
				ps.setInt(1, t.getNumber());
				ps.setString(2, t.getAuthor());
				ps.setString(3, t.getTitle());
				ps.setString(4, t.getPublisher());
				ps.setString(5, t.getYear());
				ps.setString(6, t.getIsbn());
				ps.setString(7, course.getNumber());
				ps.executeUpdate();
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop

		
		//insert into weeks (course)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into weeks (course)");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();			
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<Week>weeks = course.getSchedule().getWeeks();
			for (Week w : weeks) {
				ps = conn.prepareStatement("INSERT INTO Weeks (weekID, courseNumber) VALUES (?, ?);");
				ps.setInt(1, w.getWeek());
				ps.setString(2, course.getNumber());
				ps.executeUpdate();
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop		
		
		
		//insert into labs (weeks)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into labs (weeks)");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();			
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<Week>weeks = course.getSchedule().getWeeks();
			for (Week w : weeks) {
				List<Lab> labs  = w.getLabs();
				for (Lab l: labs) {
					ps = conn.prepareStatement("INSERT INTO Labs (weekID, labTitle, labID, labURL) VALUES (?, ?, ?, ?);");
					ps.setInt(1, w.getWeek());
					ps.setString(2, l.getTitle());
					ps.setInt(3, l.getNumber());
					ps.setString(4, l.getUrl());
					ps.executeUpdate();
				}
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop	
			

		//insert into labfiles(weeks)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into labfiles(weeks)");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();			
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<Week>weeks = course.getSchedule().getWeeks();
			for (Week w : weeks) {
				List<Lab> labs  = w.getLabs();
				for (Lab l: labs) {
					List<File> lab_files = l.getFiles();
					if (lab_files != null) {
						for (File lf : lab_files) {
							ps = conn.prepareStatement("INSERT INTO LabFiles (fileNumber, fileTitle, fileURL, labTitle) VALUES (?, ?, ?, ?);");
							ps.setInt(1, lf.getNumber());
							ps.setString(2, lf.getTitle());
							ps.setString(3, lf.getUrl());
							ps.setString(4, l.getTitle());
							ps.executeUpdate();
						}
					}
				}
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} catch (NullPointerException npe) {
			System.out.println ("NullPointerException: " + npe.getMessage());
		}
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop
			

		//insert into lectures(weeks)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into lectures(weeks)");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();			
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<Week>weeks = course.getSchedule().getWeeks();
			for (Week w : weeks) {
				List<Lecture> lectures  = w.getLectures();
				for (Lecture lec : lectures) {
					ps = conn.prepareStatement("INSERT INTO Lectures (lectureDate, lectureID, lectureDay, weekID) VALUES (?, ?, ?, ?);");
					ps.setString(1, lec.getDate());
					ps.setInt(2, lec.getNumber());
					ps.setString(3, lec.getDay());
					ps.setInt(4, w.getWeek());
					ps.executeUpdate();
				}
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} catch (NullPointerException npe) {
			System.out.println ("NullPointerException: " + npe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop				
		
			
		//insert into topics (lectures)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into topics (lectures)");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();			
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<Week>weeks = course.getSchedule().getWeeks();
			for (Week w : weeks) {
				List<Lecture> lectures  = w.getLectures();
				for (Lecture lec : lectures) {
					List<Topic> topics = lec.getTopics();
					for (Topic top : topics) {
						ps = conn.prepareStatement("INSERT INTO Topics (topicTitle, topicNumber, topicURL, topicChapter, lectureDate) VALUES (?, ?, ?, ?, ?);");
						ps.setString(1, top.getTitle());
						ps.setInt(2, top.getNumber());
						ps.setString(3, top.getUrl());
						ps.setString(4, top.getChapter());
						ps.setString(5, lec.getDate());
						ps.executeUpdate();
					}
				}
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} catch (NullPointerException npe) {
			System.out.println ("NullPointerException: " + npe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop	

		//insert into programs
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into programs (topics)");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();			
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<Week>weeks = course.getSchedule().getWeeks();
			for (Week w : weeks) {
				List<Lecture> lectures  = w.getLectures();
				for (Lecture lec : lectures) {
					List<Topic> topics = lec.getTopics();
					for (Topic top : topics) {
						List<Program> programs = top.getPrograms();
						if (programs != null) {
							for (Program p : programs) {
								ps = conn.prepareStatement("INSERT INTO Programs (topicTitle) VALUES (?);");
								ps.setString(1, top.getTitle());
								ps.executeUpdate();
							}
						}
					}
				}
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} catch (NullPointerException npe) {
			System.out.println ("NullPointerException: " + npe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop
				
				
		//insert into lecture files(weeks)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into lecture files(weeks)");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();			
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<Week>weeks = course.getSchedule().getWeeks();
			for (Week w : weeks) {
				List<Lecture> lectures  = w.getLectures();
				for (Lecture lec: lectures) {
					List<Topic> topics = lec.getTopics();
					if (topics != null) {
						for (Topic t : topics) {
							List<Program> programs = t.getPrograms();
							if (programs != null) {
								for (Program p : programs) {
									List<File> topic_files = p.getFiles();
									if (topic_files != null) {
										for (File f : topic_files) {
											ps = conn.prepareStatement("INSERT INTO LectureFiles (lectureDate, fileNumber, fileTitle, fileURL, topicTitle) VALUES (?, ?, ?, ?, ?);");
											ps.setString(1, lec.getDate());
											ps.setInt(2, f.getNumber());
											ps.setString(3, f.getTitle());
											ps.setString(4, f.getUrl());
											ps.setString(5, t.getTitle());
											ps.executeUpdate();
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop
		
		
		//insert into assignments(course)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into assignments(course)");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();			
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<Assignment> assignments = course.getAssignments();
			for (Assignment a : assignments) {
				ps = conn.prepareStatement("INSERT INTO Assignments (assignmentNumber, assignedDate, dueDate, assignTitle, assignURL, gradePercentage, courseNumber) VALUES (?, ?, ?, ?, ?, ?, ?);");
				ps.setString(1, a.getNumber());
				ps.setString(2, a.getAssignedDate());
				ps.setString(3, a.getDueDate());
				ps.setString(4, a.getTitle());
				ps.setString(5, a.getUrl());
				ps.setString(6, a.getGradePercentage());
				ps.setString(7, course.getNumber());
				ps.executeUpdate();
			}
			
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} catch (NullPointerException npe) {
			System.out.println ("NullPointerException: " + npe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop		
		
		//insert into assignmentFiles(assignments)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into assignmentFiles(assignments)");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();			
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<Assignment> assignments = course.getAssignments();
			for (Assignment a : assignments) {
				List<File> assign_files = a.getFiles();
				if (assign_files != null) {
					for (File f : assign_files) {
						ps = conn.prepareStatement("INSERT INTO AssignmentFiles (fileNumber, fileTitle, fileURL, assignmentNumber) VALUES (?, ?, ?, ?);");
						ps.setInt(1, f.getNumber());
						ps.setString(2, f.getTitle());
						ps.setString(3, f.getUrl());
						ps.setString(4, a.getNumber());
						ps.executeUpdate();
					}
				}
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} catch (NullPointerException npe) {
			System.out.println ("NullPointerException: " + npe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop
		
		
		//insert into assignmentGCF(assignments)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into assignmentGCF(assignments)");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();			
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<Assignment> assignments = course.getAssignments();
			for (Assignment a : assignments) {
				List<File> assign_files = a.getGradingCriteriaFiles();
				if (assign_files != null) {
					for (File f : assign_files) {
						ps = conn.prepareStatement("INSERT INTO AssignmentGCFs (GCFileNumber, GCFileTitle, GCFileURL, assignmentNumber) VALUES (?, ?, ?, ?);");
						ps.setInt(1, f.getNumber());
						ps.setString(2, f.getTitle());
						ps.setString(3, f.getUrl());
						ps.setString(4, a.getNumber());
						ps.executeUpdate();
					}
				}
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} catch (NullPointerException npe) {
			System.out.println ("NullPointerException: " + npe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop	
		
		
		//insert into assignmentSF(assignments)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into assignmentSF(assignments)");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();			
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<Assignment> assignments = course.getAssignments();
			for (Assignment a : assignments) {
				List<File> assign_files = a.getSolutionFiles();
				if (assign_files != null) {
					for (File f : assign_files) {
						ps = conn.prepareStatement("INSERT INTO AssignmentSFs (SFileNumber, SFileTitle, SFileURL, assignmentNumber) VALUES (?, ?, ?, ?);");
						ps.setInt(1, f.getNumber());
						ps.setString(2, f.getTitle());
						ps.setString(3, f.getUrl());
						ps.setString(4, a.getNumber());
						ps.executeUpdate();
					}
				}
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} catch (NullPointerException npe) {
			System.out.println ("NullPointerException: " + npe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop

		
		//insert into deliverables(assignments)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into deliverables(assignments)");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();			
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<Assignment> assignments  = course.getAssignments();
			for (Assignment a : assignments) {
				List<Deliverable> deliverables = a.getDeliverables();
				if (deliverables != null) {
					for (Deliverable d : deliverables) {
						ps = conn.prepareStatement("INSERT INTO Deliverables (delivNumber, dueDate, title, gradePercentage, assignmentNumber) VALUES (?, ?, ?, ?, ?);");
						ps.setString(1, d.getNumber());
						ps.setString(2, d.getDueDate());
						ps.setString(3, d.getTitle());
						ps.setString(4, d.getGradePercentage());
						ps.setString(5, a.getNumber());
						ps.executeUpdate();
					}
				}
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} catch (NullPointerException npe) {
			System.out.println ("NullPointerException: " + npe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop
		
		
		//insert into deliverableFiles(assignments)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into deliverableFiles(assignments");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();			
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<Assignment> assignments  = course.getAssignments();
			for (Assignment a : assignments) {
				List<Deliverable> deliverables = a.getDeliverables();
				if (deliverables != null) {
					for (Deliverable d : deliverables) {
						List<File> delivFiles = d.getFiles();
						if (delivFiles != null) {
							for (File df : delivFiles) {
								ps = conn.prepareStatement("INSERT INTO DeliverableFiles (delivFileNum, delivFileTitle, delivFileURL, delivNumber) VALUES (?, ?, ?, ?);");
								ps.setInt(1, df.getNumber());
								ps.setString(2, df.getTitle());
								ps.setString(3, df.getUrl());
								ps.setString(4, d.getNumber());
								ps.executeUpdate();
							}
						}
					}
				}
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} catch (NullPointerException npe) {
			System.out.println ("NullPointerException: " + npe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop		
		
		
		
		//insert into exams (courses)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into exams (courses)");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();			
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<Exam> exams = course.getExams();
			for (Exam e : exams) {
				ps = conn.prepareStatement("INSERT INTO Exams (examYear, examSemester, courseNumber) VALUES (?, ?, ?);");
				ps.setString(1, e.getYear());
				ps.setString(2, e.getSemester());
				ps.setString(3, course.getNumber());
				ps.executeUpdate();
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} catch (NullPointerException npe) {
			System.out.println ("NullPointerException: " + npe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop	
		
		
		//insert into tests (exams)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into tests (exams)");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();			
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<Exam> exams = course.getExams();
			for (Exam e : exams) {
				List<Test> tests = e.getTests();
				for (Test t : tests) {
					ps = conn.prepareStatement("INSERT INTO Tests (testTitle, examYear, examSemester) VALUES (?, ?, ?);");
					ps.setString(1, t.getTitle());
					ps.setString(2, e.getYear());
					ps.setString(3, e.getSemester());
					ps.executeUpdate();
				}
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} catch (NullPointerException npe) {
			System.out.println ("NullPointerException: " + npe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop	
		
		
		//insert into testfiles (tests)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("insert into testfiles (tests)");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();			
			Course course = school.getDepartments().get(0).getCourses().get(0);
			List<Exam> exams = course.getExams();
			for (Exam e : exams) {
				List<Test> tests = e.getTests();
				for (Test t : tests) {
					List<File> test_files = t.getFiles();
					for (File tf : test_files) {
						ps = conn.prepareStatement("INSERT INTO TestFiles (testFileNumber, testFileTitle, testFileURL, testTitle, examYear, examSemester) VALUES (?, ?, ?, ?, ?, ?);");
						ps.setInt(1, tf.getNumber());
						ps.setString(2, tf.getTitle());
						ps.setString(3, tf.getUrl());
						ps.setString(4, t.getTitle());
						ps.setString(5, e.getYear());
						ps.setString(6, e.getSemester());
						ps.executeUpdate();
					}
				}
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} catch (NullPointerException npe) {
			System.out.println ("NullPointerException: " + npe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		} //end of finally loop		
		
		
	}

	public boolean isPopulated(String connection) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();
			
			ps = conn.prepareStatement("SELECT * FROM Courses");
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
		return false;
	}

}
