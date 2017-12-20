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
	<%@ page import = "shastri.Lecture" %>
	<%@ page import = "shastri.Exam" %>
	<%@ page import = "shastri.Test" %>
	
	<% Course csci201 = top.getSchools().get(0).getDepartments().get(0).getCourses().get(0); %>
	<table cellpadding="10" width="800">
	      <tr>
	        <!-- column for left side of page -->
	        <td width="180" valign="top" align="right">
        		<a href="http://www.usc.edu"><img src=<%= top.getSchools().get(0).getImage()%> border="0"/></a><br /><br />          
          	<font size="+1"><a href="http://cs.usc.edu"><%=top.getSchools().get(0).getDepartments().get(0).getPrefix()%> Department</a></font><br /><br />
          	<font size="+1"><a href="frontpage.jsp"><%=top.getSchools().get(0).getDepartments().get(0).getPrefix() + " " + csci201.getNumber() %> Home</a></font><br /><br />         
          	<font size="+1"><a href=<%=csci201.getSyllabus().getUrl()%>>Syllabus</a></font><br /><br />
          	<font size="+1"><a href="lectures.jsp">Lectures</a></font><br /><br />
          	<font size="+1"><a href="assignments.jsp">Assignments</a></font><br /><br />
          	<font size="+1">Previous Exams</font><br /><br />
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
	          <p><hr size="4" /></p>
	
	          <table border="2" cellpadding="5" width="590">
	            <tr>
	              <th align="center">Semester</th><th align="center">Written Exam #1</th><th align="center">Programming Exam #1</th><th>Written Exam #2</th><th>Programming Exam #2</th>
	            </tr>
	            <%for(int i = 0; i < csci201.getExams().size(); i++) { %>
	            <tr>
	            	<% Exam currExam = csci201.getExams().get(i); %>
		            <td align="center"><%=currExam.getSemester()%> <%=currExam.getYear()%></td>
		            
		            <td align="center">
		            <%//first get tests for each exam object %>
		              <%for(int j = 0; j < currExam.getTests().size(); j++) {%>
		              	<% Test currentTest = currExam.getTests().get(j);%>
		              	<% //null check for test title %>
		              	<% if (currentTest.getTitle() != null) { %>
			              	<% if(currentTest.getTitle().equals("Written Exam #1")) { %>
			              		<% //null check for files size %>
			              		<% if (currentTest.getFiles().size() > 0) { %>
				              		<%for(int k = 0; k < currentTest.getFiles().size(); k++) { %>
				              			<% //null check for url %>
				              			<% if (currentTest.getFiles().get(k).getUrl() != null) { %> 
					              			<a href=<%=currentTest.getFiles().get(k).getUrl() %>><%=currentTest.getFiles().get(k).getTitle()%></a>
						              		<% if(!(currentTest.getFiles().size() == 1 || k == currentTest.getFiles().size()-1)) { %>
						              			<hr />
								            <% } %>
								        <% } %>
				           			<% } %>   	
				           		<% } %>	
			            		<% } %> 
			           	<% } %>
		            <% } %>
		            </td>
		            
		            <td align="center">
		             <%//first get tests for each exam object %>
		              <%for(int j = 0; j < currExam.getTests().size(); j++) {%>
		              	<% Test currentTest = currExam.getTests().get(j);%>
		              	<% //null check for test title %>
		              	<% if (currentTest.getTitle() != null) { %>
			              	<% if(currentTest.getTitle().equals("Programming Exam #1")) { %>
			              		<% //null check for files size %>
			              		<% if (currentTest.getFiles().size() > 0) { %>
				              		<%for(int k = 0; k < currentTest.getFiles().size(); k++) { %>
				              			<% //null check for url %>
				              			<% if (currentTest.getFiles().get(k).getUrl() != null) { %> 
					              			<a href=<%=currentTest.getFiles().get(k).getUrl() %>><%=currentTest.getFiles().get(k).getTitle()%></a>
						              		<%if(!(currentTest.getFiles().size() == 1 || k == currentTest.getFiles().size()-1)) {%>
						              			<hr />
								            <% } %>
								        <% } %>
				           			<% } %>   	
				           		<% } %>	
			            		<% } %> 
			           	<% } %>
		            <% } %>
		            </td>
		            
		            	<td align="center">
		             <%//first get tests for each exam object %>
		              <%for(int j = 0; j < currExam.getTests().size(); j++) {%>
		              	<% Test currentTest = currExam.getTests().get(j);%>
		              	<% //null check for test title %>
		              	<% if (currentTest.getTitle() != null) { %>
			              	<% if(currentTest.getTitle().equals("Written Exam #2")) { %>
			              		<% //null check for files size %>
			              		<% if (currentTest.getFiles().size() > 0) { %>
				              		<%for(int k = 0; k < currentTest.getFiles().size(); k++) { %>
				              			<% //null check for url %>
				              			<% if (currentTest.getFiles().get(k).getUrl() != null) { %> 
					              			<a href=<%=currentTest.getFiles().get(k).getUrl() %>><%=currentTest.getFiles().get(k).getTitle()%></a>
						              		<%if(!(currentTest.getFiles().size() == 1 || k == currentTest.getFiles().size()-1)) {%>
						              			<hr />
								            <% } %>
								        <% } %>
				           			<% } %>   	
				           		<% } %>	
			            		<% } %> 
			           	<% } %>
		            <% } %>
		            </td>
		            
		            <td align="center">
		             <%//first get tests for each exam object %>
		              <%for(int j = 0; j < currExam.getTests().size(); j++) {%>
		              	<% Test currentTest = currExam.getTests().get(j);%>
		              	<% //null check for test title %>
		              	<% if (currentTest.getTitle() != null) { %>
			              	<% if(currentTest.getTitle().equals("Programming Exam #2")) { %>
			              		<% //null check for files size %>
			              		<% if (currentTest.getFiles().size() > 0) { %>
				              		<%for(int k = 0; k < currentTest.getFiles().size(); k++) { %>
				              			<% //null check for url %>
				              			<% if (currentTest.getFiles().get(k).getUrl() != null) { %> 
					              			<a href=<%=currentTest.getFiles().get(k).getUrl() %>><%=currentTest.getFiles().get(k).getTitle()%></a>
						              		<%if(!(currentTest.getFiles().size() == 1 || k == currentTest.getFiles().size()-1)) {%>
						              			<hr />
								            <% } %>
								        <% } %>
				           			<% } %>   	
				           		<% } %>	
			            		<% } %> 
			           	<% } %>
		            <% } %>
		            </td>
		            </tr>
	            <% } %>
	          </table>
	        </td>
	      </tr>
	    </table>
	</body>
</html>