<%@ page import="objects.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="parsing.StringConstants"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Vector"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <% String searchInput = request.getParameter("search");
 if (searchInput.isEmpty()){
		return;
	}
 
	String changedSearchInput = searchInput.toLowerCase();
	HttpSession session2 = request.getSession();
	DataContainer school2 = (DataContainer) session.getAttribute(StringConstants.DATA);
	//new vector of correct IDs
	Vector<Integer> matchingIDs = new Vector<Integer>();
	//iterate through all staff
	List<StaffMember> staff = school2.getSchools().get(0).getDepartments().get(0).getCourses().get(0).getStaffMembers();
	for (int i = 0; i < staff.size();i++) {
		//if the search input is equal to the first last or full name of staff
		if (staff.get(i).getName().getFirstName().toLowerCase().equals(changedSearchInput) || 
			staff.get(i).getName().getLastName().toLowerCase().equals(changedSearchInput) ||
			staff.get(i).getName().getFullName().toLowerCase().equals(changedSearchInput)) {
			matchingIDs.add(staff.get(i).getID());
		}
	}

	String output = "";
	if (matchingIDs.size() == 1) {
		output += matchingIDs.get(0);
		//responseText now has the value of output
		%><%=output%>
		<% return;
	}
	//size greater than 1
	for (int l = 0; l < matchingIDs.size(); l++) {
		output += matchingIDs.get(l);
		//if we reach end of the list
		if (l == matchingIDs.size()-1) {
			break;
		}
		output += ", ";
	} %>
	<%=output%>
	<%return;%>