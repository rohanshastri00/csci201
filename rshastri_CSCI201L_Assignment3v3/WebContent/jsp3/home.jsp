<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link rel="stylesheet" type="text/css" href="css1.css" />
<% request.setAttribute(StringConstants.LOCATION, StringConstants.HOME_JSP); %>
<%@include file="nav_bar.jsp"%>
<!--  the start tags here that are commented out are included in the nav_bar.jsp
<html>
<body>
	<table>
		<tr>
			<td> -->
				<script type="text/javascript">
				var trimmedID = null;
				var IDList = null;
				var IDListArr = [];
				function validate() {					
					var xhttp = new XMLHttpRequest();
					xhttp.open("GET", "validate.jsp?search=" + document.myform.search.value, false);
					xhttp.send();
					
					var returnedText = xhttp.responseText;
					trimmedID = returnedText.trim();				
					console.log("trimmedID: " + trimmedID);
					
					
					if (trimmedID.length > 1) {
						var trimmedIDLen = trimmedID.length;
						var temp = "";
						for (var i = 0; i < trimmedIDLen; i++) {
							if (trimmedID[i] == ",") {
								IDListArr.push(temp);
								//reset temp
								temp = "";
							} else {
								temp += IDListArr[i];
							}
						}
						
						if (temp != "") {
							IDListArr.push(temp);
						} 	
					}
					
					
					IDList = document.getElementsByClassName(trimmedID);
					for (var i = 0; i < IDList.length;i++) {
						IDList[i].style.backgroundColor = "#ADD8E6";
					}  
				}
				
				function clearSearch() {
					if (trimmedID !== null) {
						IDList = document.getElementsByClassName(trimmedID);	
						for (var i = 0; i < IDList.length;i++) {
							IDList[i].style.backgroundColor = "transparent";
						} 		
					}
					//add for multiple IDs?
				}
				
				</script>
			
				<div id="searchBarText"> 
					<form name="myform">
						<input type="text" name="search" placeholder="Search Staff Members"/>
 						<input type="button" value="Search Staff" onclick = "clearSearch(), validate()">
 						<input type="button" value ="Clear Search" onclick = "clearSearch()">
					</form>
					
				</div>

				
 				<p>
				<hr size="4" />
				</p>
				<p></p> 
				<div id = "instructorBarText" class = 'bounce'>
				<table border="1">
					<tr>
						<th align="center">Picture</th>
						<th align="center">Professor</th>
						<th align="center">Office</th>
						<th align="center">Phone</th>
						<th align="center">Email</th>
						<th align="center">Office Hours</th>
					</tr>
				</div>
				<% 
				List<StaffMember> professors = course.getSortedStaff().get(StringConstants.INSTRUCTOR);
              	for (int i = 0; i<professors.size(); i++)
              	{ 
            		StaffMember prof = professors.get(i);
              	%>
					<tr class = "<%= prof.getID() %>">
					<div class = 'bounce'>
						<td><img width="100" height="100" src="<%= prof.getImage() %>" /></td>
						<td><font size="-1"><%= prof.getName().getFirstName() %> <%= prof.getName().getLastName() %></font></td>
						<td><font size="-1"><%= prof.getOffice() %></font></td>
						<td><font size="-1"><%= prof.getPhone() %></font></td>
						<td><font size="-1"><%= prof.getEmail() %></font></td>
						<td><font size="-1"> 
					</div>
					<%
                	List<TimePeriod> oh = prof.getOH();
                	for (int j = 0; j<oh.size(); j++)
                	{
                		TimePeriod current = oh.get(j);
                
                	%> 
                		<%= current.getDay() %> <%= current.getTime().getStartTime() %>- <%= current.getTime().getEndTime() %>
								<hr /> 
					<%
                  	}
               		%>
						</font></td>
					</tr>
				<%
              	}
             	%>
				</table> <br />
				<p>
				<hr size="4" />
				</p> <b><font size="+1">Lecture Schedule</font></b> 
				<%
          		List<Meeting> lectures = course.getSortedMeetings().get(StringConstants.LECTURE);
          		%>
				<table border="2" cellpadding="5" width="590">
					<tr>
						<th align="center">Sect. No.</th>
						<th align="center">Day &amp; Time</th>
						<th align="center">Room</th>
						<th>Lecture Assistant</th>
					</tr>
					<div class = "lectureSection">
				<% 
				for (int i = 0; i<lectures.size(); i++)
				{
	            	Meeting lecture = lectures.get(i);
            	%>	
					<tr>
						<td align="center"><font size="-1"><%= lecture.getSection() %></font></td>
						<td align="center"><font size="-1"><%= lecture.getListedDays() %><br /><%= lecture.getListedTimes() %></font></td>
						<td align="center"><font size="-1"><%= lecture.getRoom() %></font></td>
					<%
              		Map<Integer, StaffMember> staffMap = course.getMappedStaff();
					
              		for (int p = 0; p<lecture.getAssistants().size(); p++)
              		{
            	  		Integer id = lecture.getAssistants().get(p).getID();
            	  		StaffMember staffMember = staffMap.get(id);
              		%>
					<td class = "<%= staffMember.getID() %>" align="center"><font size="-1"><img src="<%= staffMember.getImage() %>"><br /> 
							<a href="mailto:<%= staffMember.getEmail() %>"><%= staffMember.getName().getFirstName() %>
									<%= staffMember.getName().getLastName() %></a></font></td>
					<%
              		}
              		%>
					</tr>
				<%
            	}
            	%>
            </div>
				</table>
				<p>
				<hr size="4" />
				</p> <b><font size="+1">Lab Schedule</font></b>
				<table border="2" cellpadding="5" width="590">
					<tr>
						<th align="center">Sect. No.</th>
						<th align="center">Day &amp; Time</th>
						<th align="center">Room</th>
						<th align="center">Lab Assistants</th>
					</tr>
				<%
            	List<Meeting> labs = course.getSortedMeetings().get(StringConstants.LAB);
				
            	for (int i = 0; i<labs.size(); i++)
            	{
	            	Meeting lab = labs.get(i);
           		%>
					<tr>
						<td align="center"><font size="-1"><%= lab.getSection() %></font></td>
						<td align="center"><font size="-1"><%= lab.getListedDays() %><br /><%= lab.getListedTimes() %></font></td>
						<td align="center"><font size="-1"><%= lab.getRoom() %></font></td>
						<td align="center">
							<table border="0">
								<tr>
					<%
	              	Map<Integer, StaffMember> staffMap = course.getMappedStaff();
									
	              	for (int p = 0; p<lab.getAssistants().size(); p++)
	              	{
	            	  	Integer id = lab.getAssistants().get(p).getID();
	            	  	StaffMember staffMember = staffMap.get(id);
	             	%>
									<td class = "<%= staffMember.getID() %>" align="center"><font size="-1"><img src="<%= staffMember.getImage() %>"><br /> 
										<a href="mailto:<%= staffMember.getEmail() %>"><%= staffMember.getName().getFirstName() %>
												<%= staffMember.getName().getLastName() %></a></font></td>
					<%
	              	}
	              	%>
								</tr>
							</table>
						</td>
					</tr>
				<% 
				} 
				%>
				</table> <br />
				<hr size="4" /> <br /> <b><font size="+1">Office Hours</font></b>
				- All staff office hours are held in the SAL Open Lab. Prof.
				Miller's office hours are listed above.<br />
				<table border="1">
					<tr>
						<th></th>
				<% 
				for (String time : Course.officeHourTimes)
				{ 
				%>
						<th><%= time %></th>
				<%
				} 
				%>
					</tr>
				<% 
				StaffMember [][] officeHours = course.getOfficeHours();
 			  	String [] days = Course.officeHourDays;
 			  	
              	for (int i= 0; i<6; i++)
              	{  
              	%>
					<tr>
						<th><%= days[i] %></th>
					<% 
					for (int j = 0; j< 5; j++)
					{ 
            	  		StaffMember current = officeHours[i][j];
            	  		if (current != null)
            	  		{
              		%>
						<td class = "<%= current.getID() %>">
							<table border="0">
								<tr>
									<td><img src="<%= current.getImage() %>" /></td>
								</tr>
								<tr>
									<td><font size="-1"><a href="mailto:<%= current.getEmail() %>"><%= current.getName().getFirstName() %>
												<%= current.getName().getLastName() %></a><br />&nbsp;</font></td>
								</tr>
							</table>
						</td>
						<% 
						} 
              			else
              			{
              			%>
						<td></td>
					<%
              			}
            	  	} 
            	  	%>
					</tr>
				<%
				} 
				%>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>