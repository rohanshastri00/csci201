package servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import objects.Assignment;
import objects.Course;
import objects.GeneralAssignment;
import parsing.StringConstants;

public class GenAssignSorter {
	public List<GeneralAssignment> sort(String type, String sortBy, String dir, Course course) {
		List<GeneralAssignment> generalAssigns = new ArrayList<GeneralAssignment>();
		
		String sortParam = "";
		if(sortBy.equals(StringConstants.TITLE)) {
			sortParam = "assignTitle";
		} else if (sortBy.equals(StringConstants.DUE)) {
			sortParam = "dueDate";
		} else if (sortBy.equals(StringConstants.ASSIGNED)) {
			sortParam = "assignedDate";
		} else if (sortBy.equals(StringConstants.GRADE)) {
			sortParam = "gradePercentage";
		}
		
		String dirParam = "";
		if (dir.equals(StringConstants.ASCENDING)) {
			dirParam = "ASC";
		} else if (dir.equals(StringConstants.DESCENDING)) {
			dirParam = "DESC";
		}
		
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String connection  = course.getConnectionString();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(connection);
			st = conn.createStatement();
			
			if (type.equals(StringConstants.DELIVERABLES)) {
				ps = conn.prepareStatement("SELECT * FROM Assignments WHERE assignmentNumber = 'Final Project' ORDER BY " + sortParam  + " " + dirParam + ";");
			}
			if (type.equals(StringConstants.ASSIGNMENTS)) {
				ps = conn.prepareStatement("SELECT * FROM Assignments WHERE assignmentNumber <> 'Final Project' ORDER BY " + sortParam  + " " + dirParam + ";");
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				Assignment a = new Assignment();
				a.setTitle(rs.getString("assignTitle"));
				a.setDueDate(rs.getString("dueDate"));
				a.setAssignedDate(rs.getString("assignedDate"));
				a.setGradePercentage(rs.getString("gradePercentage"));
				a.setNumber(rs.getString("assignmentNumber"));
				a.setUrl(rs.getString("assignURL"));
				generalAssigns.add(a);
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

		
		return generalAssigns;
	}
}
