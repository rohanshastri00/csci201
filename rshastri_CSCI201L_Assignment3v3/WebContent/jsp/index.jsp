<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
	<form action="${pageContext.request.contextPath}/ChooseFile"
		method="post" enctype="multipart/form-data">
		<div>
			<label>Please choose a JSON file</label> <br /> <input accept=".json"
				type="file" id="file" name="file">
		</div>
		<div>
			<input id = "design1" type="radio" name="css" value="design1" /> 
		    <label for = "design1">Design 1</label><br />
		    <input id = "design2" type="radio" name="css" value="design2" /> 
		    <label for = "design2">Design 2</label><br />		    
		
			<input type="submit" value="Upload File" />
		</div>
		
	</form>
</body>
</html>

