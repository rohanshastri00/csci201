<%@ page import="objects.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="parsing.StringConstants"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Collections"%>
<%@ page import="java.util.Comparator"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

 <% HttpSession session2 = request.getSession();
	DataContainer school2 = (DataContainer) session.getAttribute(StringConstants.DATA); 
	Course course2 =  school2.getSchools().get(0).getDepartments().get(0).getCourses().get(0);
	Schedule schedule2 = course2.getSchedule(); %>
	
 <% 
 
 	String assignSort = request.getParameter("ASort");

	String delivSort = request.getParameter("DSort");
	String sortBy = "";
 	
 	List<Assignment> assignments = course2.getAssignments();


	//SORTING VIA DUE DATE
	if (assignSort.equals("dueDate")){
 	  try {
			Collections.sort(assignments, new Comparator<Assignment>(){
			
				public int compare (Assignment a1, Assignment a2) {
					String a1Date = a1.getDueDate();
					
			        String[] a1_values = a1Date.split("-");
			        int a1_month = Integer.parseInt(a1_values[0]);
			        int a1_day = Integer.parseInt(a1_values[1]);
			        int a1_year = Integer.parseInt(a1_values[2]);

			        String a2Date = a2.getDueDate();
					
					String[] a2_values = a2Date.split("-");
			        int a2_month = Integer.parseInt(a2_values[0]);
			        int a2_day = Integer.parseInt(a2_values[1]);
			        int a2_year = Integer.parseInt(a2_values[2]);

				 	//for ascending sort
				 	if (request.getParameter("order").equals("ascending")) {
						System.out.println("ascending order");
						int c = Integer.compare(a1_year, a2_year);
					    if (c == 0)
					    		c = Integer.compare(a1_month, a2_month);
					    if (c == 0)
					    		c = Integer.compare(a1_day, a2_day);
					    return c;
					}
					else {  
						System.out.println("descending order");
						int c = Integer.compare(a2_year, a1_year);
						
						System.out.println(c);
					    if (c == 0)
					    		c = Integer.compare(a2_month, a1_month);
					    
						System.out.println(c);
					    if (c == 0)
					    		c = Integer.compare(a2_day, a1_day);
					    
						System.out.println(c);

					    return c;
					}
				}	
			});
		} catch (NullPointerException npe) {}
	}	
	
	System.out.println("Due date sorted, now printing assignments");
	for (int i = 0; i < assignments.size();i++) {
		System.out.println("Assignment Date: " + assignments.get(i).getDueDate());
	} 
 	
 	
	%>
	
	<table border="2" cellpadding="5" width="890">
					<tr>
						<th align="center">Homework #</th>
						<th align="center" width="100">Assigned</th>
						<th align="center" width="100">Due</th>
						<th>Assignment</th>
						<th>% Grade</th>
						<th>Grading Criteria</th>
						<th>Solution</th>
					</tr>
					<%
				    for (Assignment assignment : course2.getAssignments())
				    {
				      	if (!assignment.equals(course2.getFinalProject()))
				      	{ 
					%>
					<tr>
						<td align="center"><%= assignment.getNumber() %></td>
						<td align="center"><%= assignment.getAssignedDate() %></td>
						<td align="center"><%= assignment.getDueDate() %></td>
						<td align="center">
							<% 
							if (assignment.getTitle() != null)
				            { 
				            %> 
				            <a href="<%= assignment.getUrl() %>"><%= assignment.getTitle() %></a>
							<hr /> 
							<%  	
				            } 
				            if (assignment.getFiles() != null)
				            { 
				            	for (File file : assignment.getFiles())
				            	{ 
				            %> 
				            <a href="<%= file.getUrl() %>"><%= file.getTitle() %></a>
							<hr /> 
							<%
				               	} 
				           	 }
				           	 %>
						</td>
						<td align="center"><%= assignment.getGradePercentage() %></td>
						<td align="center">
							<% 
							if (assignment.getGradingCriteriaFiles() != null)
							{ 
							 	for (File file : assignment.getGradingCriteriaFiles())
							 	{ 
							%> 	  
							<a href="<%= file.getUrl() %>"><%= file.getTitle() %></a>
							<hr /> 
							<%
								} 
							}
							%>
						</td>
						<td align="center">
							<% 
							if (assignment.getSolutionFiles() != null)
				            { 
				            	for (File file : assignment.getSolutionFiles())
				            	{ 
				           	%> 
				           <a href="<%= file.getUrl() %>"><%= file.getTitle() %></a>
							<hr /> 
							<%
								} 
				            }
				            %>
						</td>
					</tr>
					<%
						} 
					}
				    Assignment finalProject = course2.getFinalProject();
				       
			       	if (finalProject != null)
			       	{ 
			   	    %>
					<tr>
						<td align="center"><%= finalProject.getNumber() %></td>
						<td align="center"><%= finalProject.getAssignedDate() %></td>
						<td align="center" colspan="3">
							<table border="1" cellpadding="5">
								<tr>
									<td colspan="3" align="center"><a
										href="<%= finalProject.getUrl() %>"><%= finalProject.getTitle() %></a>
										<hr /> 
						<% 
						for (File file : finalProject.getFiles()) 
						{
						%> 
				        				<a href="<%= file.getUrl() %>"><%= file.getTitle() %></a>
				
						<% 
						} 
						%>
									</td>
								</tr>
						<% 
						for (Deliverable del : finalProject.getDeliverables())
				        { 
				        %>
								<tr>
									<td><%= del.getDueDate() %></td>
									<td><%= del.getTitle() %> 
							<% 
							if (del.getFiles() != null) 
				            {
				            %> 		<br /> 
				            	<% 
				            	for (File file : del.getFiles())
				                { 
				                %> 
				                	<a href="<%= file.getUrl() %>"><%= file.getTitle() %></a>
										<hr /> 
							<%
								} 
				            }
				            %>
				            		</td>
									<td><%= del.getGradePercentage() %></td>
								</tr>
						<%
						} 
						%>
							</table>
						</td>
					</tr>
					<%
					}
					%>
				</table> 