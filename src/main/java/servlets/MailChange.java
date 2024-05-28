package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import utils.Util;

import java.io.IOException;

@WebServlet(name = "mailChangeServlet", urlPatterns = "/mailChange")
public class MailChange extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession ses = request.getSession();
	     int id = ((User) ses.getAttribute("user")).getId();
	     
	     String newMail = (String) request.getParameter("user_newMail");
	     String pass = (String) request.getParameter("user_MailPass");
	     
	     Util.sendMailChangeMsg(ses, id, pass, newMail);
	     
	     try {
	    	 response.sendRedirect("./jsp/datosPersonales.jsp");
	     }catch (IOException e) {
	    	 e.printStackTrace();
	     }
	}

}
