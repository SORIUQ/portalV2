package utils;

import connections.Conector;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.HashMap;

public class Util {

    public static boolean loginProcess(HttpServletRequest req, HttpSession session) {
        boolean landing = false;
        Conector conector = new Conector();
        Connection con = null;
        try {
            con = conector.getMySqlConnection();
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM credentials WHERE email = ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String hashedPassFromDB = resultSet.getString("pass");
                if(BCrypt.checkpw(password, hashedPassFromDB)) {
                    User user = createUser(con, resultSet);
                    session.setAttribute("user", user);
                    landing = true;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return landing;
    }

    public static User createUser(Connection con, ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM user_obj WHERE id = ?");
        preparedStatement.setInt(1, user.getId());
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            user.setUserType(resultSet.getString("user_type"));
            user.setName(resultSet.getString("user_name"));
            user.setId_school(resultSet.getInt("school_id"));
            user.setId_course(resultSet.getInt("course_id"));
        }
        System.out.println(user);
        return user;
    }

    public static void insertCredentials(Connection conn, String userEmail, String userPass) {
        try (PreparedStatement ps = conn.prepareStatement("call insertUserCredentials(?,?)")) {
            ps.setString(1, userEmail);
            ps.setString(2, userPass);
            int linesModified = ps.executeUpdate();
            if(linesModified > 0)
                System.out.println("Query ejecutada con éxito!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertUserInDb(Connection conn, String name, String lastName, String birthDate, String dnie, String email, String pass, String schoolId, String courseId) {
        try (PreparedStatement ps = conn.prepareStatement("call insertUser(?,?,?,?,?,?,?,?,?);");) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setString(3, birthDate);
            ps.setString(4, dnie);
            ps.setString(5, "01");
            ps.setString(6, email);
            ps.setString(7, pass);
            // Preguntarnos si los ids la mejor forma de tratarlos sería con Strings y no ints.
            ps.setInt(8, Integer.parseInt(schoolId));
            ps.setInt(9, Integer.parseInt(courseId));
            int linesModified = ps.executeUpdate();
            if(linesModified > 0)
                System.out.println("Query ejecutada con éxito!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIfEmailExist(Connection conn, String email) {
        boolean res = false;
        try (PreparedStatement ps = conn.prepareStatement("select * from credentials where email=?;")) {
            ps.setString(1, email);
            try {
                ResultSet rs = ps.executeQuery();
                res = rs.next();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public static boolean checkIfDnieExist(Connection conn, String dnie) {
        boolean res = false;
        try (PreparedStatement ps = conn.prepareStatement("select * from user_obj where dnie=?;")) {
            ps.setString(1, dnie);
            try {
                ResultSet rs = ps.executeQuery();
                res = rs.next();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}
