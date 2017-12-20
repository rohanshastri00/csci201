package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
		String css = request.getParameter("css");

		Part filePart = request.getPart(StringConstants.FILE); 
		 InputStreamReader fileContent = new InputStreamReader(filePart.getInputStream());
		 BufferedReader br = new BufferedReader(fileContent);
		 DataContainer data = new Parser(br).getData();
		 br.close();
		 fileContent.close();
		 data.organize();
		 request.getSession().setAttribute(StringConstants.DATA, data);
		 request.getSession().setMaxInactiveInterval(60);
		 if("design1".equals(css)) {
			 System.out.print("I go in here");
			 response.sendRedirect("jsp3/home.jsp");
		 }
		 if("design2".equals(css)) {
			 response.sendRedirect("jsp2/" + StringConstants.HOME_JSP);
		 }
	}

}
