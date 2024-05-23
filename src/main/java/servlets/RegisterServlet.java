package servlets;

import connections.Conector;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import modelsDAO.UserDAO;
import org.mindrot.jbcrypt.BCrypt;
import utils.Util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession ses = req.getSession();
        Connection con = null;
        // Datos del usuario
        String userName = req.getParameter("user_name");
        String userLastName = req.getParameter("user_lastname");
        String userEmail = req.getParameter("user_email");
        String userBirthday = req.getParameter("user_birthday");
        String userDnie = req.getParameter("user_nif");
        String userSchool = req.getParameter("user_center");
        String userCourse = req.getParameter("user_course");
        String userPass = req.getParameter("user_password");
        String hashedPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
        String userExists = "El usuario ya esta registrado";

        try {
            con = new Conector().getMySqlConnection();
            boolean emailExists = Util.checkIfEmailExist(con, userEmail);
            boolean dnieExists = Util.checkIfDnieExist(con, userDnie);
            if (con != null) {
                if (emailExists || dnieExists) {
                    if (emailExists)
                        userExists += "<br/>Email ya existe";
                    if (dnieExists)
                        userExists += "<br/>DNIE ya existe";
                    ses.setAttribute("userExists", userExists);
                } else {
                    UserDAO.insertCredentials(con, userEmail, hashedPass);
                    UserDAO.insertUserInDb(con, userName, userLastName, userBirthday, userDnie, userEmail, hashedPass, userSchool, userCourse);
                    userExists = "¡Usuario registrado con éxito!";
                    ses.setAttribute("userExists", userExists);
                }
                res.sendRedirect("./jsp/login.jsp");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
