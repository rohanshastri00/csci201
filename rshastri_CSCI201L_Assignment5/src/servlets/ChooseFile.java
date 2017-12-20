package servlets;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import objects.DataContainer;
import parsing.Parser;
import parsing.StringConstants;

/**
 * Servlet implementation class ChooseFile
 */
@WebServlet("/ChooseFile")
@MultipartConfig
public class ChooseFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ipaddress = request.getParameter("ipaddress");
		String useSSL = request.getParameter("SSL");
		if (useSSL == null) { //it's not checked
			useSSL = "false";
		} else {
			useSSL = "true";
		}
		
		//building the string connection
		String connection = "jdbc:mysql://" + ipaddress + "/rshastri_201_site?user=" + username + "&password=" + password + "&useSSL=" + useSSL;
		
		Boolean dbPopulated = false;
		JSONPopulate jp = new JSONPopulate();
		try {
			Part filePart = request.getPart(StringConstants.FILE);

			InputStream fC = filePart.getInputStream();
			Scanner scan = new Scanner(fC);
			String catcher = scan.useDelimiter("\\Z").next();
			
			
			InputStreamReader fileContent = new InputStreamReader(filePart.getInputStream());
			BufferedReader br = new BufferedReader(fileContent);
			DataContainer data = new Parser(br).getData();
			
			br.close();
			fileContent.close();
			if (data != null) { //json has been provided
				//jp = new JSONPopulate();
				jp.populate(connection, data);
				data.organize();
				dbPopulated = true; //there is data in the db
			}
		} catch(NoSuchElementException nsee) {
			if (jp.isPopulated(connection)) {
				dbPopulated = true;
			}
		} catch (FileNotFoundException fnfe) { } 
		catch (NullPointerException npe) { } 
		
		ObjectBuilder ob = new ObjectBuilder();
		if (dbPopulated) { //if the db is populated
			//regardless of json being submitted, we have to buildObjects
			ob.buildObjects(request, response, connection);	
		}
		if (ob.redirected == false ) { 
			response.sendRedirect("jsp/index.jsp");
		}
		
		
	}

}
