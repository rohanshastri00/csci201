<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="shastri.TopLevel" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>USC: CSCI 201L Fall 2017</title>
</head>
  <body text="#333333" bgcolor="#EEEEEE" link="#0000EE" vlink="#551A8B" alink="#336633">


  <% TopLevel top =  (TopLevel)session.getAttribute("Object"); %>
     <% if (top == null) { %>
		<% request.getRequestDispatcher("/index.jsp").forward(request,response); %>
	<% } %>
  	<%@ page import = "shastri.Course" %> 
  	<%@ page import = "shastri.OfficeHour" %> 
	<%@ page import = "java.util.List" %>
	<%@ page import = "java.util.ArrayList" %>
	<%@ page import = "java.util.Arrays" %>	
	<%@ page import = "shastri.Time" %>
	<%@ page import = "shastri.School" %>		
	<%@ page import = "shastri.Meeting" %>
	<%@ page import = "shastri.MeetingPeriod" %>
	<%@ page import = "shastri.StaffMember" %>
	<%@ page import = "shastri.Assistant" %>
	<% Course csci201 = top.getSchools().get(0).getDepartments().get(0).getCourses().get(0); %>

    
    <table cellpadding="10" width="800">
      <tr>
        <!-- column for left side of page -->
        <td width="180" valign="top" align="right">
          <a href="http://www.usc.edu"><img src=<%= top.getSchools().get(0).getImage()%> border="0"/></a><br /><br />
          <font size="+1"><a href="http://cs.usc.edu"><%=top.getSchools().get(0).getDepartments().get(0).getPrefix()%> Department</a></font><br /><br />
          <font size="+1"><%=top.getSchools().get(0).getDepartments().get(0).getPrefix() + " " + csci201.getNumber() %> Home</font><br /><br />
          <font size="+1"><a href=<%=csci201.getSyllabus().getUrl()%>>Syllabus</a></font><br /><br />
          <font size="+1"><a href="lectures.jsp">Lectures</a></font><br /><br />
          <font size="+1"><a href="assignments.jsp">Assignments</a></font><br /><br />
          <font size="+1"><a href="exams.jsp">Previous Exams</a></font><br /><br />
        </td>
        <!-- center column to separate other two columns -->
        <td width="5"><br /></td>
        <!-- large column in center of page with dominant content -->
        <td align="baseline" width="615">
          <br />
          <p>
            <b><font size="+3"><%=top.getSchools().get(0).getDepartments().get(0).getPrefix() + " " + csci201.getNumber() %></font></b><br />
            <b><i><font size="+1"><%=csci201.getTitle() + " - " + csci201.getUnits() %> units</font></i></b><br />
            <b><i><font size="+1"><%=csci201.getTerm() + " " + csci201.getYear() %></font></i></b><br />          
          <p><hr size="4" /></p>
          <p></p>
          <p><font size="-1">
            <table border="1">
              <tr>
                <th align="center">Picture</th>
                <th align="center">Professor</th>
                <th align="center">Office</th>
                <th align="center">Phone</th>
                <th align="center">Email</th>
                <th align="center">Office Hours</th>
              </tr>
              <tr>
				<% for(int i = 0; i < csci201.getStaffMembers().size();i+=1) { %>
				<% if (csci201.getStaffMembers().get(i).getType().equals("instructor")) { %>
				<td><img width="100" height="100" src=<%=csci201.getStaffMembers().get(i).getImage()%> /></td>
				<td><font size="-1"><%=csci201.getStaffMembers().get(i).getName().getFname() + " " + csci201.getStaffMembers().get(i).getName().getLname() %></font></td>
				<td><font size="-1"><%=csci201.getStaffMembers().get(i).getOffice() %></font></td>
                <td><font size="-1"><%=csci201.getStaffMembers().get(i).getPhone() %></font></td>
                <td><font size="-1"><%=csci201.getStaffMembers().get(i).getEmail() %></font></td>
				<td><font size="-1"> 
				<% List<OfficeHour> specificStaffHours = csci201.getStaffMembers().get(i).getOfficeHours();%>
				<% for(int j =0; j < specificStaffHours.size(); j+=1){ %>
				
				<%	String Day = specificStaffHours.get(j).getDay();
					String timeStart = specificStaffHours.get(j).getTime().getStart();
					String timeEnd = specificStaffHours.get(j).getTime().getEnd();
					String Final = Day + " " + timeStart + "-" + timeEnd;
				%>
				<%=Final %><hr />
			<% } %>Any day by appointment<hr />
				</font></td>
              </tr>
				<% } %>
			<% } %>            
            </table>
          <br />
          
          <p><hr size="4" /></p>
          <b><font size="+1">Lecture Schedule</font></b>
          <table border="2" cellpadding="5" width="590">
            <tr>
              <th align="center">Sect. No.</th><th align="center">Day &amp; Time</th><th align="center">Room</th><th>Lecture Assistant</th>
            </tr>
            <tr>
            <% //get the list of all meetings %>
              <% List<Meeting> meetings = csci201.getMeetings();%>
              <% for (int i = 0; i < meetings.size();i++)  { %>
            <% //isolate lectures, will work even if there is more than 1 lecture %>            
              	<% if (meetings.get(i).getType().equals("lecture")) { %>
              		<td align="center"><font size="-1"><%=meetings.get(i).getSection()%></font></td>
              		<%List<MeetingPeriod> meetingPeriod = meetings.get(i).getMeetingPeriods();%>
              		<td align="center"><font size="-1"><%=meetingPeriod.get(0).getDay()+"/"+meetingPeriod.get(1).getDay()%><br />
              		<%=meetingPeriod.get(0).getTime().getStart()+"-"+meetingPeriod.get(0).getTime().getEnd()%></font></td>
              		<td align="center"><font size="-1"><%=meetings.get(i).getRoom()%></font></td>
              		<% //find staffmembers info through the ID given in the meeting %>
              		<%List<StaffMember> staffMember = csci201.getStaffMembers();%>
					<%String currentID = meetings.get(i).getAssistants().get(0).getStaffMemberID();%>
					
              		<%for (int l = 0; l < staffMember.size(); l++) { %>
              			<% if (staffMember.get(l).getId().equals(currentID)) { %>
              			    <% String staffImage = staffMember.get(l).getImage(); %>
              			    <% String staffEmail = staffMember.get(l).getEmail(); %>
							<% String staffName = staffMember.get(l).getName().getFname() + " " + staffMember.get(l).getName().getLname(); %>
              			    <td align="center"><font size="-1"><img src=<%=staffImage%>><br /><a href=<%=staffEmail%>><%=staffName%></a></font></td>
              			<% } %>
              		<% } %>
              		</tr>
             	 <% } %>
              <% } %>
              <%//can assume that no quiz section will have any staffMembers %>
              <% for (int i = 0; i < meetings.size();i++)  { %>
              	<% if (meetings.get(i).getType().equals("quiz")) { %>
              		<td align="center"><font size="-1"><%=meetings.get(i).getSection()%></font></td>
              		<%List<MeetingPeriod> meetingPeriod = meetings.get(i).getMeetingPeriods();%>
              		<td align="center"><font size="-1"><%=meetingPeriod.get(0).getDay()+ " " + 
              		meetingPeriod.get(0).getTime().getStart()+"-"+meetingPeriod.get(0).getTime().getEnd()%></font></td>
              		<td align="center"><font size="-1"><%=meetings.get(i).getRoom()%></font></td>              	
              	<% } %>
              <% } %>
          </table>
          
          <p><hr size="4" /></p>
          <b><font size="+1">Lab Schedule</font></b>
          <table border="2" cellpadding="5" width="590">
            <tr>
              <th align="center">Sect. No.</th><th align="center">Day &amp; Time</th><th align="center">Room</th><th align="center">Lab Assistants</th>
            </tr>
              <% for (int i = 0; i < meetings.size();i++)  { %>
              	<% if (meetings.get(i).getType().equals("lab")) { %>
              	 <tr>
              		<td align="center"><font size="-1"><%=meetings.get(i).getSection()%></font></td>
              		<%List<MeetingPeriod> meetingPeriod = meetings.get(i).getMeetingPeriods();%>
              		<td align="center"><font size="-1"><%=meetingPeriod.get(0).getDay()%><br />
              		<%=meetingPeriod.get(0).getTime().getStart()+"-"+meetingPeriod.get(0).getTime().getEnd()%></font></td>
              		<td align="center"><font size="-1"><%=meetings.get(i).getRoom()%></font></td>
              		
              		<%List<StaffMember> staffMember = csci201.getStaffMembers();%>					
				<td align="center">
                		<table border="0">
                 		<tr>
				<%for (int k = 0; k < meetings.get(i).getAssistants().size(); k++) { %>				
              		<%for (int l = 0; l < staffMember.size(); l++) { %>
              			<%String currentID = meetings.get(i).getAssistants().get(k).getStaffMemberID();%>          			
              			<% if (staffMember.get(l).getId().equals(currentID)) { %>
              			    <% String staffImage = staffMember.get(l).getImage(); %>
              			    <% String staffEmail = staffMember.get(l).getEmail(); %>
							<% String staffName = staffMember.get(l).getName().getFname() + " " + staffMember.get(l).getName().getLname(); %>
              			    	<td align="center"><font size="-1"><img src=<%=staffImage%>><br /><a href=<%=staffEmail%>><%=staffName%></a></font></td>
              			<% } %>
              		<% } %>
              	<% } %>
              				</tr>
              			</table>
              		</td>
            		</tr>
             	 <% } %>
              <% } %>
		</table>
          <br /><hr size="4" /><br />
            <b><font size="+1">Office Hours</font></b> - All staff office hours are held in the SAL Open Lab.  Prof. Miller's office hours are listed above.<br />
            <table border="1">
              <tr>
                <th></th>
                <th>10:00a.m.-12:00p.m.</th>
                <th>12:00p.m.-2:00p.m.</th>
                <th>2:00p.m.-4:00p.m.</th>
                <th>4:00p.m.-6:00p.m.</th>
                <th>6:00p.m.-8:00p.m.</th>
              </tr>
              <%//lists created to make printing much simpler %>
           <% List<String> days = new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"));%>
           <% List<String> time = new ArrayList<>(Arrays.asList("10:00a.m.", "12:00p.m.", "2:00p.m.", "4:00p.m.", "6:00p.m."));%>
              	
              	<%//iterate through the given days, for each day + time, make call to function in Course.java %>
              	<% for (int i = 0; i < days.size();i++) { %>
              	<tr> 
              		<th><%=days.get(i) %> </th>           	
              			<% for (int j = 0; j < time.size(); j++) { %>
              			    <%StaffMember currentStaff = csci201.findCorrectOHTime(days.get(i), time.get(j));%>
              			    <td><img src = <%=currentStaff.getImage() %>><br><a href="mailto:<%=currentStaff.getEmail() %>">
              			    <%=currentStaff.getName().getFname() %> <%=currentStaff.getName().getLname() %></a></td>
              			<% } %>
              	</tr>
              	<% } %>
            </table> 
	</body>
</html>