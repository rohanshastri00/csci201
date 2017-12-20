<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<link rel="stylesheet" type="text/css" href="css1.css" />
<% request.setAttribute(StringConstants.LOCATION, StringConstants.LECTURES_JSP); %>
<%@include file="nav_bar.jsp"%>
<% 
	Schedule schedule = course.getSchedule(); 
%>
<!--  the start tags here that are commented out are included in the nav_bar.jsp
<html>
<body>
	<table>
		<tr>
			<td> -->
				<script>
				var matches = [];
				var cell_matches = null;
				function validate() {					
					var xhttp = new XMLHttpRequest();
					xhttp.open("GET", "validateLecture.jsp?search=" + document.myform.search.value, false);
					xhttp.send();
					//get list of matches, trim it
					var returnedText = xhttp.responseText;
					var trimmedMatch = returnedText.trim();
					
					var trimmedStringLen = trimmedMatch.length;
					var temp = "";
					
					for (var i = 0; i < trimmedStringLen; i++) {
						if (trimmedMatch[i] == ",") {
							matches.push(temp);
							//reset temp
							temp = "";
						} else {
							temp += trimmedMatch[i];
						}
					}
					
					if (temp != "") {
						matches.push(temp);
					}
				
					
					for (var j = 0; j < matches.length; j++) {
						//for every topic name in matches we get the cell(s) that match
						cell_matches = document.getElementsByClassName(matches[j]);
						for (var k = 0; k < cell_matches.length; k++) {
							cell_matches[k].style.backgroundColor = "#ADD8E6";
						}
					}
				}
				
				function clearSearch() {
					
					console.log ("Matches: " + matches[0]);
					
				 	if (matches.length > 0) {
						for (var j = 0; j < matches.length; j++) {
							//for every topic name in matches we get the cell(s) that match
							cell_matches = document.getElementsByClassName(matches[j]);
							for (var k = 0; k < cell_matches.length; k++) {
								cell_matches[k].style.backgroundColor = "transparent";
							}
						}  
				 	}
					matches = []; 
				}
				
				function checkDD() {
						var xhttp = new XMLHttpRequest();
						xhttp.open("GET", "filterDueDates.jsp", false);
						xhttp.send();
						var response = xhttp.responseText;
						//replace current table with new one from filterDueDates.jsp
						document.getElementById("lecturesTable").innerHTML = response;
				}
				
				function checkLec() {
						var xhttp = new XMLHttpRequest();
						xhttp.open("GET", "filterLectures.jsp", false);
						xhttp.send();
						var response = xhttp.responseText;
						console.log(response);
						//replace current table with new one from filterLectures.jsp
						document.getElementById("lecturesTable").innerHTML = response;
				}
				
				function showAll() {
						var xhttp = new XMLHttpRequest();
						xhttp.open("GET", "filterAll.jsp", false);
						xhttp.send();
						var response = xhttp.responseText;
						//replace current table with new one from filterAll.jsp
						document.getElementById("lecturesTable").innerHTML = response;	
				}
				
				function disableButton() {
					document.getElementById("searchButton").disabled = true;
					document.getElementById("clearButton").disabled = true;
					document.getElementById("textBox").disabled = true;
				}
			
				function enableButton() {
					document.getElementById("searchButton").disabled = false;
					document.getElementById("clearButton").disabled = false;
					document.getElementById("textBox").disabled = false;
				}
				</script>
				
				
					
				<% 
        		for (int i = 0; i<schedule.getTextbooks().size(); i++)
        		{
      	  			Textbook text = schedule.getTextbooks().get(i);
        		%>
				<p>
				<hr size="4" />
				</p> <b>Chapter references are from <%= text.getAuthor() %> <u><%= text.getTitle() %>
				</u>, <%= text.getPublisher() %>, <%= text.getYear() %>. ISBN <%= text.getIsbn() %></b>
				<%
				} 
				%>
				<p>
				<hr size="4" />
				<form name="myform">
					<input id = "textBox" type="text" name="search" placeholder="Search Topics"/>
 					<input id = "searchButton" type="button" value="Search Topics" onclick = "clearSearch(), validate()">
 					<input id = "clearButton" type="button" value ="Clear Search" onclick = "clearSearch()">
				</form> <hr>
					<input name = "filter" type="radio" value="showLec" onclick = "enableButton(), checkLec()"><label for = "showLec">Show Lectures</label>
					<input name = "filter" type="radio" value="ShowDD" onclick = "disableButton(), checkDD()" ><label for = "ShowDD">Show Due Dates</label>
					<input name = "filter" type = "radio" value="showAll" onclick = "enableButton(), showAll()" /><label for = "showAll">Show All</label>
					
				<hr size="4" />
				</p> <b>Lectures</b>
			<div id = "lecturesTable">
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
            		for (Week week : schedule.getWeeks())
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
		</div>
	</body>
</html>
