package shastri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.Gson;


@MultipartConfig
@WebServlet("/JSONImport")
public class JSONImport extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public JSONImport() {
        // TODO Auto-generated constructor stub
    }
    
    TopLevel school = null;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part filePart = request.getPart("index"); 
		InputStream fileContent = filePart.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(fileContent));
		Gson parse = new Gson();
		school = parse.fromJson(br, TopLevel.class);
		HttpSession session = request.getSession();
		session.setAttribute("Object", school);
		session.setMaxInactiveInterval(60);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/frontpage.jsp");
		rd.forward(request, response);		
		//response.sendRedirect("frontpage.jsp");
	}

}
