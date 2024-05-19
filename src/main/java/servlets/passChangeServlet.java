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
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession ses = request.getSession();
		int id = ((User) ses.getAttribute("user")).getId();
		String oldPass = request.getParameter("user_oldPassword");
		String newPass = request.getParameter("user_newPassword");

		
		try {
			if (Util.checkPassDB(id, oldPass)){
				UserDAO.setNewPass(id, newPass);
				ses.setAttribute("okMsg", "Se ha cambiado la contraseña con exito");
				response.sendRedirect("./jsp/datosPersonales.jsp");
			}else {
				ses.setAttribute("errorMsg", "La contraseña proporcionada no coincide con la base de datos");
			    response.sendRedirect("./jsp/datosPersonales.jsp");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
