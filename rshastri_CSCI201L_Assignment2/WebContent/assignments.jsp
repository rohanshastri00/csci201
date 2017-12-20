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
	<%@ page import = "shastri.Assignment" %>
	<%@ page import = "shastri.File" %>
	<%@ page import = "shastri.GradingCriteriaFile" %>
	<%@ page import = "shastri.SolutionFile" %>
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
          <font size="+1"><a href="lectures.jsp">Lectures</a></font><br /><br />
          <font size="+1">Assignments</font><br /><br />
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
          <p><hr size="4" /></p>

          <b>Assignments</b>
          <table border="2" cellpadding="5" width="590">
            <tr>
              <th align="center">Homework #</th><th align="center">Assigned</th><th align="center" width="40">Due</th><th>Assignment</th><th>% Grade</th><th>Grading Criteria</th><th>Solution</th>
            </tr>
            <% List<Assignment> assignments = csci201.getAssignments();%>
            <% for (int i = 0; i < assignments.size()-1; i++) { %>
            <% Assignment currAssign = assignments.get(i);%>
            <tr>
            <% //Hmwk Number %>
              <td align="center"><%=currAssign.getNumber()%></td>
            <% //Assignment Date %>
              <td align="center"><%=currAssign.getAssignedDate()%></td>
            <% //Due Date %>
              <td align="center"><%=currAssign.getDueDate()%></td>
            <% //Assignment PDF, Files %>
              <td align="center">
              <% if(currAssign.getUrl() != null && currAssign.getTitle() != null) { %>
              	<a href=<%=currAssign.getUrl()%>><%=currAssign.getTitle() %></a><hr />
              <% } %>            
              <% if(currAssign.getFiles() != null) { %>
			  <% List<File> assignFiles = currAssign.getFiles(); %>
	          	<% for (int j = 0; j < assignFiles.size(); j++) { %>
	          		<a href=<%=assignFiles.get(j).getUrl() %>><%=assignFiles.get(j).getTitle()%></a>
	              	<% if(!(assignFiles.size() == 1 || j == assignFiles.size()-1)) { %>
	              		<hr />
	              	<% } %>
	            <% } %>
              <% } %>
              </td>
             <% //Grade Percentage %>
              <td align="center"><%=currAssign.getGradePercentage() %></td>
             <% //Grading Criteria Files %>              
              <td align="center">
              <% if(currAssign.getGradingCriteriaFiles() != null) { %>
              	<% List<GradingCriteriaFile> gcf = currAssign.getGradingCriteriaFiles(); %>
              	<% for(int j = 0; j < gcf.size(); j++)  {%>
                		<a href=<%=gcf.get(j).getUrl() %>><%=gcf.get(j).getTitle()%></a><br />
               	<% } %>
              <% } %>
              </td>
             <% //Solution  Files %>                    
              <td align="center">
              <% if(currAssign.getSolutionFiles() != null) { %>
	              <% List<SolutionFile> sf = currAssign.getSolutionFiles(); %>
	              <% for(int j = 0; j < sf.size(); j++)  { %>
	                		<a href=<%=sf.get(j).getUrl() %>><%=sf.get(j).getTitle()%></a><br />
	              <% } %>
              <%} %>
              </td>
            </tr>
            <%} %>   
                     
            <% //FINAL PROJECT INFO %>        
     		<tr>
            <% Assignment finalAssign = assignments.get(assignments.size()-1);%>
              <td align="center">Final Project</td>
              <td align="center"><%=finalAssign.getAssignedDate()%></td>
              
              <% //printing out project descriptions w URLs %>
              <td align="center" colspan="3">
                <table border="1" cellpadding="5">
                  <tr>
                    <td colspan="3" align="center">
 					<% if(finalAssign.getUrl() != null) { %>
              			<a href=<%=finalAssign.getUrl()%>><%=finalAssign.getTitle() %></a><hr />
              		<% } %> 
              		<% //now printing any additional files %>
					<% if(finalAssign.getFiles() != null) { %>
						<% List<File> finalFiles = finalAssign.getFiles(); %>
						<% for (int j = 0; j < finalFiles.size(); j++) { %>
			          		<a href=<%=finalFiles.get(j).getUrl() %>><%=finalFiles.get(j).getTitle()%></a>
			              	<% if(!(finalFiles.size() == 1 || j == finalFiles.size()-1)) { %>
			              			<hr />
			              	<% } %>
						<% } %>
					<% } %>
                    </td>
                  </tr>
                  
                  <% //now printing deliverables %>
                  <%for(int j = 0; j < finalAssign.getDeliverables().size(); j++) { %>
                  <% Deliverable currDeliverable = finalAssign.getDeliverables().get(j); %>
                  <tr>
                    <td><%=currDeliverable.getDueDate()%></td>
                    
                    <td><%=currDeliverable.getTitle() %>

                      <% if(currDeliverable.getFiles() != null) { %>
		              	<% for (int k = 0; k < currDeliverable.getFiles().size(); k++) { %>
		              		<% List<File> delivFiles = currDeliverable.getFiles(); %>
		              		<a href=<%=delivFiles.get(k).getUrl()%>><%=delivFiles.get(k).getTitle()%></a>
		              		<% if(!(delivFiles.size() == 1 || k == delivFiles.size()-1)) { %>
		              			<hr />
		              		<% } %>
		              	<% } %>
		              <% } else { %>
		              	<br />
		              <% } %>
                    </td>
                    <td><%=currDeliverable.getGradePercentage()%></td>
                    </tr>
                   <%} %> 
                </table><% // end of final project table %>
              </td>
            </tr>
          </table> <% // end of assignments table %>
        </td>
      </tr>
    </table>
  </body>
</html>
