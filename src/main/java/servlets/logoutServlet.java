package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "logoutServlet", urlPatterns = "/logout")
public class logoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    HttpSession ses = request.getSession();
	    
	    if (ses!= null) {
	      ses.setAttribute("user", null);
	      ses.invalidate();
	      response.sendRedirect("index.jsp");
	    }

	}

}
