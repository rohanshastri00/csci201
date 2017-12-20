<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="parsing.StringConstants"%>
<html>
<body>
	<form action="${pageContext.request.contextPath}/ChooseFile"
		method="post" enctype="multipart/form-data">

		<div>
		
			<label>Enter MySQL username</label>
			<input type = "text" name = "username"><br />
			<label>Enter MySQL password (can be blank)</label>
			<input type = "text" name = "password"><br />
			<label>Enter MySQL ipaddress</label>
			<input type = "text" name = "ipaddress"><br />
			<label>Choose whether to use SSL</label>
			<input type = "checkbox" name = "SSL" value = "ssl"><br /><br />
		</div>
		
		<div>
			<label>If you would like, you can uplaod a json file containing data you wish to insert into the database</label><br />
			<input type = "file" name = "file"><br />
		</div>
		<div>
			<br />
			<input type="radio" name="<%= StringConstants.DESIGN %>" value="<%= StringConstants.DESIGN_1 %>" checked> Design 1<br>
  			<input type="radio" name="<%= StringConstants.DESIGN %>" value="<%= StringConstants.DESIGN_2 %>"> Design 2<br>
 		</div>
 			<br />
 		<div>
			<input type="submit" value="Connect" />
		</div>
	</form>
</body>
</html>
