<%@ page import="objects.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="parsing.StringConstants"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

 <% HttpSession session2 = request.getSession();
	DataContainer school2 = (DataContainer) session.getAttribute(StringConstants.DATA); 
	Course course2 =  school2.getSchools().get(0).getDepartments().get(0).getCourses().get(0);
	Schedule schedule2 = course2.getSchedule(); %>
	
	
	<table border="2" cellpadding="5" width="790">
					<tr>
						<th align="center">Week #</th>
						<th align="center">Lab</th>
						<th align="center">Lecture #</th>
						<th align="center">Day</th>
						<th align="center" width="100">Date</th>
						<th align="center">Lecture Topic</th>
						<th align="center">Chapter</th>
						<th>Program</th>
					</tr>
					<%
            		for (Week week : schedule2.getWeeks())
            		{
            		%>
					<tr>
						<td align="center" rowspan="<%= week.getRowSize() %>"><%= week.getWeek() %></td>
						<td align="center" rowspan="<%= week.getRowSize() %>">
						<%
              			for (Lab lab : week.getLabs())
              			{
              			%> 
              				<a href="<%= lab.getUrl() %>"><%= lab.getTitle() %></a>
							<hr /> 
						<%
						}
						for (int i = 0; i<week.getLectures().size(); i++){
							
							if (i != 0)
							{
						%>
					<tr>
							<%
							}
						
             				Lecture l1 = week.getLectures().get(i);   
            				%>
						<td align="center" rowspan="1"><%= l1.getNumber() %></td>
						<td align="center" rowspan="1"><%= l1.getDay() %></td>
						<td align="center" rowspan="1"><%= l1.getDate() %></td>
						<td class = "test" align="center" rowspan="1">
						<% 
							for (Topic topic : l1.getTopics())
							{
	              			%> 
	              			<div class="<%= topic.getTitle() %>">
              				<a href="<%= topic.getUrl() %>"><%= topic.getTitle() %></a>
              				</div>
							<hr /> 
							<%
							} 
							%>
						</td>
						<td align="center" rowspan="1"><%= l1.getAllChapters() %></td>
						<td align="center" rowspan="1">
							<% 
							for (Program program : l1.getAllPrograms())
							{
	            	  			if (program.getFiles() != null){
	            	  				
	            	  				for (File file : program.getFiles()){
	              			%> 
              				<a href="<%= file.getUrl() %>"><%= file.getTitle() %></a><br />
								<%
									}
	            	  			} 
	            	  			%> 
            	  			<br /> 
	            	  		<%
	            	  		} 
	            	  		%>
						</td>
					</tr>
							<%
							List<GeneralAssignment> assigns = week.getMappedDueDates().get(l1.getNumber());
							if (assigns != null)
							{
								for (GeneralAssignment a : assigns)
								{
									String title = (a instanceof Assignment ? "ASSIGNMENT "+a.getNumber() : a.getTitle());
							%>
					<tr>
						<td align="center"></td>
						<td align="center"><%= l1.getDay() %></td>
						<td align="center"><%= a.getDueDate() %></td>
						<td align="center" colspan="3"><font size="+1" color="red"><b><%= title %> DUE</b></font></td>
					</tr>
					<%
								}
							}
						}
					}
					%>
			</table>
	
	
	