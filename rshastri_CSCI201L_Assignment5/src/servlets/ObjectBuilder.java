package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.Assignment;
import objects.Course;
import objects.DataContainer;
import objects.Deliverable;
import objects.Department;
import objects.Exam;
import objects.File;
import objects.Lab;
import objects.Lecture;
import objects.Meeting;
import objects.Name;
import objects.Program;
import objects.Schedule;
import objects.School;
import objects.StaffMember;
import objects.StaffMemberID;
import objects.Syllabus;
import objects.Test;
import objects.Textbook;
import objects.Time;
import objects.TimePeriod;
import objects.Topic;
import objects.Week;
import parsing.StringConstants;

public class ObjectBuilder {

	Boolean redirected = false;
	
	public void buildObjects(HttpServletRequest request, HttpServletResponse response, String connection) {
		//create an empty school container for the database, and the actual school
		DataContainer data = new DataContainer();
		School school = new School();
		data.getSchools().add(school);
		Department dept = new Department();
		Course course = new Course();
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();

			
			//BEGIN FILLING
			//school obj
			ps = conn.prepareStatement("SELECT * FROM Schools");
			rs = ps.executeQuery();
			while (rs.next()) {
				school.setName(rs.getString("schoolName"));
				school.setImage(rs.getString("imageLink"));
			}
			
			ps = conn.prepareStatement("SELECT * FROM Departments");
			rs = ps.executeQuery();
			while (rs.next()) {
				dept.setName(rs.getString("deptName"));
				dept.setPrefix(rs.getString("prefix"));
				school.getDepartments().add(dept);
			}
	
			
			ps = conn.prepareStatement("SELECT * FROM Courses");
			rs = ps.executeQuery();
			while (rs.next()) {
				course.setNumber(rs.getString("courseNumber"));
				course.setTitle(rs.getString("courseName"));
				course.setTerm(rs.getString("term"));
				course.setYear(rs.getInt("courseYear"));
				course.setUnits(rs.getInt("units"));
				
				Syllabus syllabus = new Syllabus();
				syllabus.setURL(rs.getString("syllabus"));
				course.setSyllabus(syllabus);
				
				dept.getCourses().add(course);
			}

			ArrayList<Meeting> meetings = new ArrayList<Meeting>();
			ps = conn.prepareStatement("SELECT * FROM Meetings");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Meeting m = new Meeting();
				m.setSection(rs.getString("section"));
				m.setType(rs.getString("meetingType"));
				m.setRoom(rs.getString("room"));
				meetings.add(m);

			}
				
			ArrayList<TimePeriod> meetingPeriods = new ArrayList<TimePeriod>();
			ps = conn.prepareStatement("SELECT * FROM MeetingPeriods");
			rs = ps.executeQuery();
			while(rs.next()) {
				TimePeriod tp = new TimePeriod();
				tp.setDay(rs.getString("meetingDay"));
				
				Time t = new Time();
				t.setStart(rs.getString("meetingStart"));
				t.setEnd(rs.getString("meetingEnd"));
				tp.setTime(t);
				
				tp.setSection(rs.getString("section"));
				
				meetingPeriods.add(tp);
			}

			//add the corresponding time periods based on section
			for (int i = 0; i < meetings.size(); i++) {
				for (int j = 0; j < meetingPeriods.size(); j++) {
					if (meetings.get(i).getSection().equals(meetingPeriods.get(j).getSection())) {
						meetings.get(i).getMeetingPeriods().add(meetingPeriods.get(j));
					}

				}
			}
			
			ArrayList<StaffMemberID> assistants = new ArrayList<StaffMemberID>();
			ps = conn.prepareStatement("SELECT * FROM Assistants");
			rs = ps.executeQuery();
			while (rs.next()) {
				StaffMemberID sm = new StaffMemberID();
				sm.setID(rs.getInt("staffMemberID"));
				sm.setSection(rs.getString("section"));
				assistants.add(sm);
			}
			
			//add the corresponding time periods based on section
			for (int i = 0; i < meetings.size(); i++) {
				for (int j = 0; j < assistants.size(); j++) {
					if (meetings.get(i).getSection().equals(assistants.get(j).getSection())) {
						meetings.get(i).getAssistants().add(assistants.get(j));
					}

				}
			}
	
			//finally add the full list of meetings
			course.setMeetings(meetings);
			
			ArrayList<StaffMember> staffMembers = new ArrayList<StaffMember>();
			ps = conn.prepareStatement("SELECT * FROM StaffMembers");
			rs = ps.executeQuery();
			while (rs.next()) {
				StaffMember mem = new StaffMember();
				mem.setID(rs.getInt("staffMemberID"));
				mem.setType(rs.getString("staffType"));
				
				Name name = new Name();
				name.setFname(rs.getString("firstName"));
				name.setLname(rs.getString("lastName"));
				
				mem.setName(name);
				
				mem.setEmail(rs.getString("email"));
				mem.setImage(rs.getString("image"));
				mem.setPhone(rs.getString("phone"));
				mem.setOffice(rs.getString("office"));
				
				staffMembers.add(mem);
			}
			
			ArrayList<TimePeriod> officeHours = new ArrayList<TimePeriod>();
			ps = conn.prepareStatement("SELECT * FROM OfficeHours");
			rs = ps.executeQuery();
			while (rs.next()) {
				TimePeriod tp = new TimePeriod();
				tp.setStaffMemberID(rs.getInt("staffMemberID"));
				
				Time t = new Time();
				t.setStart(rs.getString("startTime"));
				t.setEnd(rs.getString("endTime"));
				tp.setTime(t);
				
				tp.setDay(rs.getString("OHday"));
				
				officeHours.add(tp);
			}
			
			
			//find right OH based on ID
			for (int i = 0; i < staffMembers.size();i++) {
				for (int j = 0; j < officeHours.size();j++) {
					if (staffMembers.get(i).getID() == officeHours.get(j).getStaffMemberID()) {
						staffMembers.get(i).getOH().add(officeHours.get(j));
					}
				}
			}
			
			//add the correct staff members
			course.setStaffMembers(staffMembers);

			//building the schedule
			Schedule schedule = new Schedule();
			
			//first textbooks
			ArrayList<Textbook> textbooks = new ArrayList<Textbook>();
			ps = conn.prepareStatement("SELECT * FROM Textbooks");
			rs = ps.executeQuery();
			while (rs.next()) {
				Textbook text = new Textbook();
				text.setNumber(rs.getInt("textBookNum"));
				text.setAuthor(rs.getString("author"));
				text.setTitle(rs.getString("title"));
				text.setPublisher(rs.getString("publisher"));
				text.setYear(rs.getString("bookYear"));
				text.setIsbn(rs.getString("isbn"));
				textbooks.add(text);
			}
			
			schedule.setTextbooks(textbooks);

			
			ArrayList<Week> weeks = new ArrayList<Week>();
			ps = conn.prepareStatement("SELECT * FROM Weeks");
			rs = ps.executeQuery();
			while (rs.next()) {
				Week w = new Week();
				w.setWeek(rs.getInt("weekID"));
				weeks.add(w);
			}
			
			ArrayList<Lab> labs = new ArrayList<Lab>();
			ps = conn.prepareStatement("SELECT * FROM Labs");
			rs = ps.executeQuery();
			while (rs.next()) {
				Lab l = new Lab();
				l.setTitle(rs.getString("labTitle"));
				l.setNumber(rs.getInt("labID"));
				l.setUrl(rs.getString("labURL"));
				l.setWeek(rs.getInt("weekID"));
				labs.add(l);
			}
			
			ArrayList<File> labFiles = new ArrayList<File>();
			ps = conn.prepareStatement("SELECT * FROM LabFiles");
			rs = ps.executeQuery();
			while (rs.next()) {
				File f = new File();
				f.setNumber(rs.getInt("fileNumber"));
				f.setTitle(rs.getString("fileTitle"));
				f.setUrl(rs.getString("fileURL"));
				f.setParent(rs.getString("labTitle"));
				labFiles.add(f);
			}
			
			//now match the labfiles to the right lab
			ArrayList<File> matches = new ArrayList<File>();

			for (int i = 0; i < labs.size();i++) {
				for (int j = 0; j < labFiles.size(); j++) {
					if (labs.get(i).getTitle().equals(labFiles.get(j).getParent())) {
						matches.add(labFiles.get(j));
					}
				}
				labs.get(i).setLabFiles(matches);
				matches = new ArrayList<File>();
			}
			
			
			
			
			//now match the lab to the right week
			ArrayList<Lab> lab_matches = new ArrayList<Lab>();
			for (int i = 0; i < weeks.size();i++) {
				for (int j = 0; j < labs.size(); j++) {
					if (weeks.get(i).getWeek() == labs.get(j).getWeek()) {
						lab_matches.add(labs.get(j));
					}
				}
				weeks.get(i).setLabs(lab_matches);
				lab_matches = new ArrayList<Lab>();
			}
			
			//build the lectures
			ArrayList<Lecture> lectures = new ArrayList<Lecture>();
			ps = conn.prepareStatement("SELECT * FROM Lectures");
			rs = ps.executeQuery();
			while(rs.next()) {
				Lecture lec = new Lecture();
				lec.setDate(rs.getString("lectureDate"));
				lec.setNumber(rs.getInt("lectureID"));
				lec.setDay(rs.getString("lectureDay"));
				lec.setWeek(rs.getInt("weekID"));
				lectures.add(lec);
			}
			
			ArrayList<Topic> topics = new ArrayList<Topic>();
			ps = conn.prepareStatement("SELECT * FROM Topics");
			rs = ps.executeQuery();
			while(rs.next()) {
				Topic top = new Topic();
				top.setTitle(rs.getString("topicTitle"));
				top.setNumber(rs.getInt("topicNumber"));
				top.setUrl(rs.getString("topicURL"));
				top.setChapter(rs.getString("topicChapter"));
				top.setDate(rs.getString("lectureDate"));
				topics.add(top);
				
			}
			
			ArrayList<Program> programs = new ArrayList<Program>();
			ps = conn.prepareStatement("SELECT * FROM Programs");
			rs = ps.executeQuery();
			while(rs.next()) {
				Program p = new Program();
				p.setTitle(rs.getString("topicTitle"));
				programs.add(p);
			}
			
			ArrayList<File> lectureFiles = new ArrayList<File>();
			ps = conn.prepareStatement("SELECT * FROM LectureFiles");
			rs = ps.executeQuery();
			while(rs.next()) {
				File file = new File();
				file.setNumber(rs.getInt("fileNumber"));
				file.setTitle(rs.getString("fileTitle"));
				file.setUrl(rs.getString("fileURL"));
				file.setParent(rs.getString("topicTitle"));
				lectureFiles.add(file);
			}
			
			//link lectureFiles to programs
			List<File> file_matches = new ArrayList<File>();	
			for (int i = 0; i < programs.size();i++) {
				for (int j = 0; j < lectureFiles.size();j++) {
					if(programs.get(i).getTitle().equals(lectureFiles.get(j).getParent())) {
						file_matches.add(lectureFiles.get(j));
					}
				}
				programs.get(i).setFiles(file_matches);
				file_matches = new ArrayList<File>();
			}
			
			//link programs to topics
			List<Program> program_matches = new ArrayList<Program>();	
			for (int i = 0; i < topics.size();i++) {
				for (int j = 0; j < programs.size(); j++) {
					if (topics.get(i).getTitle().equals(programs.get(j).getTitle())) {
						program_matches.add(programs.get(j));
					}
				}
				topics.get(i).setPrograms(program_matches);
				program_matches = new ArrayList<Program>();
			}
			
			
			//link topics to lectures
			ArrayList<Topic> topic_matches = new ArrayList<Topic>();
			for (int i = 0; i < lectures.size(); i++) {
				for (int j = 0; j < topics.size();j++) {
					if (lectures.get(i).getDate().equals(topics.get(j).getDate())) {
						topic_matches.add(topics.get(j));
					}
				}
				lectures.get(i).setTopics(topic_matches);
				topic_matches = new ArrayList<Topic>();
			}
			
			//link lectures to weeks
			List<Lecture> lec_matches = new ArrayList<Lecture>();
			for (int i = 0; i < weeks.size();i++) {
				for (int j = 0; j < lectures.size(); j++) {
					if (weeks.get(i).getWeek() == lectures.get(j).getWeek()) {
						lec_matches.add(lectures.get(j));
					}
				}
				weeks.get(i).setLectures(lec_matches);
				
				List<Lecture> temp = weeks.get(i).getLectures();
				lec_matches = new ArrayList<Lecture>();
			}
			
			schedule.setWeeks(weeks);
			course.setSchedule(schedule);

			
			ArrayList<Assignment> assignments = new ArrayList<Assignment>();
			ps = conn.prepareStatement("SELECT * FROM Assignments");
			rs = ps.executeQuery();
			while(rs.next()) {
				Assignment a = new Assignment();
				a.setNumber(rs.getString("assignmentNumber"));
				a.setAssignedDate(rs.getString("assignedDate"));
				a.setDueDate(rs.getString("dueDate"));
				a.setTitle(rs.getString("assignTitle"));
				a.setGradePercentage(rs.getString("gradePercentage"));
				assignments.add(a);
			}
			
			ArrayList<File> assignmentFiles = new ArrayList<File>();
			ps = conn.prepareStatement("SELECT * FROM AssignmentFiles");
			rs = ps.executeQuery();
			while(rs.next()) {
				File f = new File();
				f.setNumber(rs.getInt("fileNumber"));
				f.setTitle(rs.getString("fileTitle"));
				f.setUrl(rs.getString("fileURL"));
				f.setParent(rs.getString("assignmentNumber"));
				assignmentFiles.add(f);
			}
			
			//link assignments to assignment files
			List<File> aFileMatches = new ArrayList<File>();
			for (int i = 0; i < assignments.size();i++) {
				for (int j = 0; j < assignmentFiles.size();j++) {
					if(assignments.get(i).getNumber().equals(assignmentFiles.get(j).getParent())) {
						aFileMatches.add(assignmentFiles.get(j));
					}
				}
				assignments.get(i).setFiles(aFileMatches);
				aFileMatches = new ArrayList<File>();
			}
			
			
			ArrayList<File> assignmentGCFs = new ArrayList<File>();
			ps = conn.prepareStatement("SELECT * FROM AssignmentGCFs");
			rs = ps.executeQuery();
			while(rs.next()) {
				File f = new File();
				f.setNumber(rs.getInt("GCFileNumber"));
				f.setTitle(rs.getString("GCFileTitle"));
				f.setUrl(rs.getString("GCFileURL"));
				f.setParent(rs.getString("assignmentNumber"));
				assignmentGCFs.add(f);
			}
			
			//link assignments to GCF files
			List<File> GCFileMatches = new ArrayList<File>();
			for (int i = 0; i < assignments.size();i++) {
				for (int j = 0; j < assignmentGCFs.size();j++) {
					if(assignments.get(i).getNumber().equals(assignmentGCFs.get(j).getParent())) {
						GCFileMatches.add(assignmentGCFs.get(j));
					}
				}
				assignments.get(i).setGCF(GCFileMatches);
				GCFileMatches = new ArrayList<File>();
			}
			
			
			
			ArrayList<File> assignmentSFs = new ArrayList<File>();
			ps = conn.prepareStatement("SELECT * FROM AssignmentSFs");
			rs = ps.executeQuery();
			while(rs.next()) {
				File f = new File();
				f.setNumber(rs.getInt("SFileNumber"));
				f.setTitle(rs.getString("SFileTitle"));
				f.setUrl(rs.getString("SFileURL"));
				f.setParent(rs.getString("assignmentNumber"));
				assignmentSFs.add(f);
			}
			
			//link assignments to SF files
			List<File> SFileMatches = new ArrayList<File>();
			for (int i = 0; i < assignments.size();i++) {
				for (int j = 0; j < assignmentSFs.size();j++) {
					if(assignments.get(i).getNumber().equals(assignmentSFs.get(j).getParent())) {
						SFileMatches.add(assignmentSFs.get(j));
					}
				}
				assignments.get(i).setSF(SFileMatches);
				SFileMatches = new ArrayList<File>();
			}
			
			
			
			ArrayList<Deliverable> deliverables = new ArrayList<Deliverable>();
			ps = conn.prepareStatement("SELECT * FROM Deliverables");
			rs = ps.executeQuery();
			while(rs.next()) {
				Deliverable d = new Deliverable();
				d.setNumber(rs.getString("delivNumber"));
				d.setDueDate(rs.getString("dueDate"));
				d.setTitle(rs.getString("title"));
				d.setGradePercentage(rs.getString("gradePercentage"));
				d.setNumber(rs.getString("assignmentNumber"));
				deliverables.add(d);
			}
			
			
			ArrayList<File> delivFiles = new ArrayList<File>();
			ps = conn.prepareStatement("SELECT * FROM DeliverableFiles");
			rs = ps.executeQuery();
			while(rs.next()) {
				File f = new File();
				f.setNumber(rs.getInt("delivFileNum"));
				f.setTitle(rs.getString("delivFileTitle"));
				f.setUrl(rs.getString("delivFileURL"));
				f.setParent(rs.getString("delivNumber"));
				delivFiles.add(f);
			}
			
			
			//link deliverables to deliv files
			List<File> delivFileMatches = new ArrayList<File>();
			for (int i = 0; i < deliverables.size();i++) {
				for (int j = 0; j < delivFiles.size();j++) {
					if(deliverables.get(i).getNumber().equals(delivFiles.get(j).getParent())) {
						delivFileMatches.add(delivFiles.get(j));
					}
				}
				deliverables.get(i).setFiles(delivFileMatches);
				delivFileMatches = new ArrayList<File>();
			}
			
			//link assignments to deliverables
			List<Deliverable> delivMatches = new ArrayList<Deliverable>();
			for (int i = 0; i < assignments.size();i++) {
				for (int j = 0; j < deliverables.size();j++) {
					if(assignments.get(i).getNumber().equals(deliverables.get(j).getNumber())) {
						delivMatches.add(deliverables.get(j));
					}
				}
				assignments.get(i).setDeliverables(delivMatches);
				delivMatches = new ArrayList<Deliverable>();
			}
			
			
			course.setAssignments(assignments);
			
			
				
			ArrayList<Exam> exams = new ArrayList<Exam>();
			ps = conn.prepareStatement("SELECT * FROM Exams");
			rs = ps.executeQuery();
			while(rs.next()) {
				Exam e = new Exam();
				e.setYear(rs.getString("examYear"));
				e.setSemester(rs.getString("examSemester"));
				exams.add(e);
			}
			
			ArrayList<Test> tests = new ArrayList<Test>();
			ps = conn.prepareStatement("SELECT * FROM Tests");
			rs = ps.executeQuery();
			while(rs.next()) {
				Test t = new Test();
				t.setTitle(rs.getString("testTitle"));
				t.setYear(rs.getString("examYear"));
				t.setSemester(rs.getString("examSemester"));
				tests.add(t);
			}
			
			ArrayList<File> testFiles = new ArrayList<File>();
			ps = conn.prepareStatement("SELECT * FROM TestFiles");
			rs = ps.executeQuery();
			while(rs.next()) {
				File f = new File();
				f.setNumber(rs.getInt("testFileNumber"));
				f.setTitle(rs.getString("testFileTitle"));
				f.setUrl(rs.getString("testFileURL"));
				f.setParent(rs.getString("testTitle"));
				f.setYear(rs.getString("examYear"));
				f.setSemester(rs.getString("examSemester"));
				testFiles.add(f);
			}
			
			//link test files to tests
			ArrayList<File> tfMatches = new ArrayList<File>();
			for (int i = 0; i < tests.size();i++) {
				for (int j = 0; j < testFiles.size();j++) {
					//semester and year need to match
					if (tests.get(i).getYear().equals(testFiles.get(j).getYear()) &&
						tests.get(i).getSemester().equals(testFiles.get(j).getSemester()) &&
						tests.get(i).getTitle().equals(testFiles.get(j).getParent())) {
						tfMatches.add(testFiles.get(j));
					}
				}
				tests.get(i).setFiles(tfMatches);
				tfMatches = new ArrayList<File>();
			}
			//link tests to exams
			ArrayList<Test> testMatches = new ArrayList<Test>();
			for (int i = 0; i < exams.size();i++) {
				for (int j = 0; j < tests.size();j++) {
					//semester and year need to match
					if (exams.get(i).getYear().equals(tests.get(j).getYear()) &&
						exams.get(i).getSemester().equals(tests.get(j).getSemester())) {
						testMatches.add(tests.get(j));
					}
				}
				exams.get(i).setTests(testMatches);
				testMatches = new ArrayList<Test>();
			}
			
			//link exams to course
			course.setExams(exams);
			course.setConnectionString(connection);

			data.organize();
			
			redirected = true;
			//all objects linked up, now set the session attribute
			request.getSession().setAttribute(StringConstants.DESIGN, (String)request.getParameter(StringConstants.DESIGN));
			request.getSession().setAttribute(StringConstants.DATA, data);
			request.getSession().setAttribute(StringConstants.COURSE, data.getSchools().get(0).getDepartments().get(0).getCourses().get(0)); 
			request.getSession().setMaxInactiveInterval(600);
			response.sendRedirect("jsp/"+StringConstants.HOME_JSP);
			
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
	}

}
