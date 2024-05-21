package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import models.*;
import modelsDAO.UserDAO;
import utils.Util;

import org.mindrot.jbcrypt.BCrypt;


@WebServlet(name = "passChangeServlet", urlPatterns = "/passChange")
public class passChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public passChangeServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	    HttpSession ses = request.getSession();
		int id = ((User) ses.getAttribute("user")).getId();
		String oldPass = request.getParameter("user_oldPassword");
		String newPass = request.getParameter("user_newPassword");

		Util.sendPassChangeMsg(ses,id,oldPass,newPass);

        try {
            response.sendRedirect("./jsp/datosPersonales.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
