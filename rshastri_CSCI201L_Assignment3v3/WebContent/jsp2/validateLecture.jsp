<%@ page import="objects.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="parsing.StringConstants"%>
<%@ page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <% String searchInput = request.getParameter("search");
 	if (searchInput.isEmpty()){
 		return;
 	}
 	
	String changedSearchInput = searchInput.toLowerCase();
	HttpSession session2 = request.getSession();
	DataContainer school2 = (DataContainer) session.getAttribute(StringConstants.DATA);
	
	
	List<Week> weeks = school2.getSchools().get(0).getDepartments().get(0).
			getCourses().get(0).getSchedule().getWeeks();
	
	List<Lecture> lectures = new ArrayList<Lecture>();
	List<Topic> topics = new ArrayList<Topic>();
	
	List<String> matchingTopics = new ArrayList<String>();

	for (int i = 0; i < weeks.size();i++) {
		//grab list of lectures
		lectures = weeks.get(i).getLectures();
		for (int j = 0; j < lectures.size();j++) {
			//grab list of topics	
			topics = lectures.get(j).getTopics();
			for (int k = 0; k < topics.size();k++) {
				//if the topic name has a substring equal to the lenght of the search input
				if (topics.get(k).getTitle().toLowerCase().indexOf(changedSearchInput) > -1) {
					matchingTopics.add(topics.get(k).getTitle());
				}
			}	
		}
	}

	String output = "";
	
	if (matchingTopics.size() == 1) {
		output += matchingTopics.get(0);
		//responseText now has the value of output
		%><%=output%>
		<% return;
	}
	//size greater than 1
	for (int l = 0; l < matchingTopics.size(); l++) {
		output += matchingTopics.get(l);
		//if we reach end of the list
		if (l == matchingTopics.size()-1) {
			break;
		}
		output += ", ";
	} %>
	<%=output%>
	