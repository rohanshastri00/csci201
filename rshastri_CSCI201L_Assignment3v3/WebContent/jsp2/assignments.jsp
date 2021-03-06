<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link rel="stylesheet" type="text/css" href="css2.css" />
<% request.setAttribute(StringConstants.LOCATION, StringConstants.ASSIGNMENTS_JSP); %>
<%@include file="nav_bar.jsp"%>
<!--  the start tags here that are commented out are included in the nav_bar.jsp
<html>
<body>
	<table>
		<tr>
			<td> -->
			
			<script>
			
			function sortAssign() {
				var xhttp = new XMLHttpRequest();
				//"validate.jsp?search=" + document.myform.search.value
				xhttp.open("GET", "sortAssignments.jsp?ASort=" + document.sortForm.ASort.value, false);
				xhttp.send();
				var response = xhttp.responseText;
				//replace current table with new one from sortAssignment.jsp
				document.getElementById("AssignsTable").innerHTML = response;
			}
			
			function sortDeliv() {
				var xhttp = new XMLHttpRequest();
				//"validate.jsp?search=" + document.myform.search.value
				xhttp.open("GET", "sortAssignments.jsp?DSort=" + document.sortForm.DSort.value, false);
				xhttp.send();
				var response = xhttp.responseText;
				//replace current table with new one from sortAssignment.jsp
				document.getElementById("AssignsTable").innerHTML = response;
			}
			
			
			
			
			
			
			</script>
			<form name = "sortForm">
				<label>Final Project Deliverables--Sort by: </label>
				<input name = "DSort" type="radio" value="dueDate"/><label for = "dueDate">Due Date</label>
				<input name = "DSort" type="radio" value="title" /><label for = "title">Title</label>
				<input name = "DSort" type="radio" value="gradePercent" /><label for = "gradePercent">Grade Percentage</label><br />
				<input name = "order" type="radio" value="ascending" /><label for = "ascending">Ascending Order</label>
				<input name = "order" type="radio" value="descending" /><label for = "descending">Descending Order</label><br /><hr>
				
				<label>Assignments--Sort by: </label>
				<input name = "ASort" type="radio" value="dueDate" onclick = "sortAssign()"/><label for = "dueDate">Due Date</label>
				<input name = "ASort" type="radio" value="assignedDate" /><label for = "assignedDate">Assigned Date</label>
				<input name = "ASort" type="radio" value="gradePercent" /><label for = "gradePercent">Grade Percentage</label><br />
				<input name = "order" type="radio" value="ascending" /><label for = "ascending">Ascending Order</label>
				<input name = "order" type="radio" value="descending" /><label for = "descending">Descending Order</label><br />
			</form>		
				<p>
				<hr size="4" />
				</p>
				<b>Assignments</b>
				<div class = "AssignsTable">
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
				    for (Assignment assignment : course.getAssignments())
				    {
				      	if (!assignment.equals(course.getFinalProject()))
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
				    Assignment finalProject = course.getFinalProject();
				       
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
				</div>
			</td>
		</tr>
	</table>
</body>
</html>
