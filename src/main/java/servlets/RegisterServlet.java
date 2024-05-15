package servlets;

import connections.Conector;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
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
        Connection conn = null;
        // Datos del usuario
        String userName = req.getParameter("user_name");
        String userLastName = req.getParameter("user_lastname");
        String userEmail = req.getParameter("user_email");
        String userBirthday = req.getParameter("user_birthday");
        String userDnie = req.getParameter("user_nif");
        String userSchool = req.getParameter("user_center"); // Falta esto por validar en el front
        String userCourse = req.getParameter("user_course"); // Falta esto por validar en el front
        String userPass = req.getParameter("user_password");
        String hashedPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
        try {
            conn = new Conector().getMySqlConnection();
            // Insertamos las credenciales del usuario separadas del resto de info.
            if(Util.checkIfEmailExist(conn, userEmail) || Util.checkIfDnieExist(conn, userDnie)) {
                ses.setAttribute("userExists" , "El usuario ya existe!");
                res.sendRedirect("./jsp/register.jsp");
            } else {
                Util.insertCredentials(conn, userEmail, hashedPass);
                Util.insertUserInDb(conn, userName, userLastName, userBirthday, userDnie, userEmail, hashedPass, userSchool, userCourse);
                res.sendRedirect("./jsp/login.jsp");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
