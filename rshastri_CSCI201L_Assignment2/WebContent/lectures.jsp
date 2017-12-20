<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="shastri.TopLevel"%> 
    
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
	<%@ page import = "java.util.List" %>
	<%@ page import = "shastri.Time" %> 	
	<%@ page import = "shastri.Week" %>
	<%@ page import = "shastri.Lecture" %>
	<%@ page import = "shastri.Lab" %>
	<%@ page import = "shastri.File" %>
	<%@ page import = "shastri.Topic" %>
	<%@ page import = "shastri.Program" %>
	<%@ page import = "shastri.Assignment" %>
	<%@ page import = "shastri.Schedule" %>
	<%@ page import = "shastri.Deliverable" %>
	

	<% Course csci201 = top.getSchools().get(0).getDepartments().get(0).getCourses().get(0); %>
	
    <table cellpadding="10" width="800">
      <tr>
        <!-- column for left side of page -->
        <td width="180" valign="top" align="right">
          <a href="http://www.usc.edu"><img src=<%= top.getSchools().get(0).getImage()%> border="0"/></a><br /><br />          
          <font size="+1"><a href="http://cs.usc.edu"><%=top.getSchools().get(0).getDepartments().get(0).getPrefix()%> Department</a></font><br /><br />
          <font size="+1"><a href="frontpage.jsp"><%=top.getSchools().get(0).getDepartments().get(0).getPrefix() + " " + csci201.getNumber() %> Home</a></font><br /><br />         
          <font size="+1"><a href=<%=csci201.getSyllabus().getUrl()%>>Syllabus</a></font><br /><br />
          <font size="+1">Lectures</font><br /><br />
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
            <b><i><font size="+1"><%=csci201.getTitle()%></font></i></b><br />
            <b><i><font size="+1"><%=csci201.getTerm() + " " + csci201.getYear()%></font></i></b><br />
            <% Schedule sched = csci201.getSchedule();%>
          <p><hr size="4" /></p>
          	<b>Chapter references are from <%=sched.getTextbooks().get(0).getAuthor() %>.
             <u><%=sched.getTextbooks().get(0).getTitle() %></u>, <%=sched.getTextbooks().get(0).getPublisher() %>,
             <%=sched.getTextbooks().get(0).getYear()%>. ISBN <%=sched.getTextbooks().get(0).getIsbn() %></b>
          <p><hr size="4" /></p>
          <b>Lectures</b>
          <table border="2" cellpadding="5" width="590">
            <tr>
              <th align="center">Week #</th><th align="center">Lab</th><th align="center">Lecture #</th><th align="center">Day</th><th align="center" width="40">Date</th><th align="center">Lecture Topic</th><th align="center">Chapter</th><th>Program</th>
            </tr>
            <%List<Week> week = csci201.getSchedule().getWeeks();%>
            <%for(int i=0; i<week.size(); i++) { %>
            <tr>
				
             <% int numLabs_week = week.get(i).getLabs().size(); %>
             <% int numLectures_week = week.get(i).getLectures().size();%>
             
             <%List<Deliverable> deliverables = csci201.getAssignments().get(csci201.getAssignments().size()-1).getDeliverables();%>
             
             <% //if the current week has as an assignment due %>
             <% for (int s = 0; s < week.get(i).getLectures().size(); s++) { %>
	             <% for (int j = 0; j < csci201.getAssignments().size(); j++) { %>
	             	<% if (week.get(i).getLectures().get(s).getDate().equals(csci201.getAssignments().get(j).getDueDate())) { %>
	             		<% numLectures_week += 1; %>
	             	<% } %>
	             	<%// checking deliverable due dates %>
	             		<% for (int k = 0; k < deliverables.size(); k++) { %>
	             			<% if (deliverables.get(k).getDueDate().equals(week.get(i).getLectures().get(s).getDate())) { %>
	             				<% numLectures_week += 1; %>	             			
	             			<% } %>
	             		<% } %>          	
	             	<% } %>
	         <% } %>
	         
	         
              <td align="center" rowspan="<%=numLectures_week %>"><%=(i+1) %></td>             
              <td align="center" rowspan="<%=numLectures_week %>">
             
              <% //PRINTING LAB NAMES + LINKS %>
              <% //THIS IS EFFECTIVELY A NULL CHECK FOR LABS %>
              <% if (numLabs_week > 0) { %>
	             <% for(int j = 0; j < numLabs_week; j++) { %>
	                <% Lab currentLab = week.get(i).getLabs().get(j);%>
	                <a href=<%=currentLab.getUrl() %>><%=currentLab.getTitle() %></a><hr />
	                <% if(currentLab.getFiles() != null) {%>
	             		<% for(int k = 0; k < currentLab.getFiles().size(); k++) {%>
	             			<a href=<%=currentLab.getFiles().get(k).getUrl()%>><%=currentLab.getFiles().get(k).getTitle()%></a><br />
	             		<% } %>
	             	<% } %>
	              <% } %>
	          <% } %>
              </td>
              
             <% //PRINTING LECTURE NAMES + LINKS %>
             <% if(numLectures_week > 0) { %>
             <% for (int k = 0; k < week.get(i).getLectures().size(); k++) { %>
	            	 <% Lecture currentLecture = week.get(i).getLectures().get(k);%>
	            	 
				  <td align="center"><%=currentLecture.getNumber()%></td>
	              <td align="center"><%=currentLecture.getDay()%></td>
	              <td align="center"><%=currentLecture.getDate() %></td>
					
	              <td align="center">
	              <% List<Topic> currTopics= currentLecture.getTopics();%>
	              <%for(int l = 0; l < currTopics.size(); l++) { %>
		                <a href=<%=currTopics.get(l).getUrl() %>><%=currTopics.get(l).getTitle()%></a>
		                	<% if(!(currTopics.size() == 1 || l == currTopics.size()-1)) { %> <hr /> <% } %>
	              <% } %>
	              </td>
	              
	              <% //PRINTING CHAPTERS %>
	              <td align="center">
				 <% for(int l = 0; l < currTopics.size(); l++) { %>
				 	<% if(currTopics.get(l).getChapter() != null) { %>
			        		<%=currTopics.get(l).getChapter()%>
			           	<%if(!(currTopics.size() == 1 || l == currTopics.size()-1)){%>, <% }%>
	                <% } %>
	              <% } %>
				 </td>
				 
				 <% //PRINTING PROGRAMS %>
	              <td align="center">
	              <% for(int l = 0; l < currTopics.size(); l++) { %>
	              	<% if(currTopics.get(l).getPrograms() != null) {%>
		              	<% List<Program> programs = currTopics.get(l).getPrograms(); %>
		              	<% for(int m = 0; m < programs.size(); m++) { %>
		            	  		<% for(int n = 0; n < programs.get(m).getFiles().size(); n++) { %>
		                			<a href=<%=programs.get(m).getFiles().get(n).getUrl() %>><%=programs.get(m).getFiles().get(n).getTitle() %></a><br />
		              		<% } %>
		              	<% } %>
	              	<% } %>
	              <%} %>
	              </td>
	       		</tr>
	       		
	       		<% //CHECKING IF ASSIGNMENT IS DUE ON SAME DAY AS LECTURE %>              
				<% List<Assignment> assignments = csci201.getAssignments();%>
				<% for (int l = 0; l < assignments.size(); l++) { %>
					<% Assignment currAssign = assignments.get(l);%>
						<% if (currentLecture.getDate().equals(currAssign.getDueDate())) { %>
							<td align="center"></td>
		              		<td align="center"><%=currentLecture.getDay()%></td>
		              		<td align="center"><%=currentLecture.getDate() %></td>
							<td align="center" colspan="3"><font size="+1" color="red">
							<% if (!(currAssign.getNumber().equals("Final Project"))) { %>
								<b>ASSIGNMENT <%=currAssign.getNumber()%> DUE</b></font></td>
           					</tr>
							<% } else { %> 
								<b>FINAL PROJECT DUE</b></font></td>
           					</tr>	
							<% } %>
						<% } %>	        	
				<% } %>	
				<%// checking deliverable due dates %>
            		<% for (int m = 0; m < deliverables.size(); m++) { %>	             		
            			<% if (deliverables.get(m).getDueDate().equals(currentLecture.getDate())) { %>
            				<% String delivName = deliverables.get(m).getTitle().toUpperCase(); %>
            				<td align="center"></td>
              			<td align="center"><%=currentLecture.getDay()%></td>
              			<td align="center"><%=currentLecture.getDate() %></td>
            				<td align="center" colspan="3"><font size="+1" color="red"><b><%=delivName%> DUE</b></font></td>
            				</tr>
            			<% } %>
            		<% } %>  
            		
    			<% } %>
            <% } %>
           <% } %>
    </table>
  </body>
</html>