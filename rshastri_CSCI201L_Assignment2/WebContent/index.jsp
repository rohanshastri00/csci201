<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>USC: CSCI 201L Fall 2017</title>
	<script>
	<%// makes sure file being selected is indeed a JSON file - not null checking %>
		function checkFileName() {
	        var fileName = document.getElementById("index").value;
	        if (fileName == "") {
	            alert("Browse to upload a valid File with .json extension");
				return false;
	        }
	        else if (fileName.split(".")[1].toUpperCase() == "JSON") {
	        		return true;
	        }
	        else {
	            alert("File with " + fileName.split(".")[1] + " is invalid. Upload a validfile with json extensions");
	            return false;
	        }
	        return true;
	        
	    }
		</script> 
</head>
<body>
	<form class = "center-align" name="myform" method="POST" action="JSONImport" enctype = "multipart/form-data">
		Please choose a JSON file <br />
		<input type="file" value="Choose file" id = "index" accept = ".json" name = "index"/><br />
		<input class = "center-align teal-text" type="submit" value="Submit" onclick = "return checkFileName();"><br />

	</form>
</body>
</html>