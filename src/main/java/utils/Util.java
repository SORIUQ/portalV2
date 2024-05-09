package utils;

import connections.Conector;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import models.User;

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
            if (resultSet.next() && resultSet.getString("pass").equals(password)) {
                User user = createUser(con, resultSet);
                session.setAttribute("user", user);
                landing = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
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



//    public static Map<String, String> errorMap(String name, String lastName, String email, String dnie, String school, String course, String pass, String doubleCheckPass) {
//        Map<String, String> errorMap = new HashMap<>();
//        String nameErr, lastNameErr, emailErr, birthDateErr, dniErr, schoolErr, courseErr, passErr, doubleCheckPassErr;
//        nameErr = validName(name) ? null : "Nombre incorrecto!";
//        lastNameErr = validName(lastName) ? null : "Apellido incorrecto!";
//        emailErr = emailValidator(email) ? null : "Email invalido!";
//        dniErr = detectIdType(dnie) ? null : "Dni/Nie incorrecto!";
//        schoolErr = schoolEnumBool(school) ? null : "Debes seleccionar un centro!";
//        courseErr = courseEnumBool(course) ? null : "Debes seleccionar un curso!";
//        passErr = passValidator(pass) ? null : "La contraseña no es correcta debe contener numeros, mayusculas y minusculas";
//        doubleCheckPassErr = pass.equals(doubleCheckPass) ? null : "Las contraseñas debes ser iguales!";
//
//        errorMap.put("nameErr", nameErr);
//        errorMap.put("lastNameErr", lastNameErr);
//        errorMap.put("emailErr", emailErr);
//        errorMap.put("dniErr", dniErr);
//        errorMap.put("schoolErr", schoolErr);
//        errorMap.put("courseErr", courseErr);
//        errorMap.put("passErr", passErr);
//        errorMap.put("doubleCheckPassErr", doubleCheckPassErr);
//
//        return errorMap;
//    }

//    public static boolean detectIdType(String id) {
//        char[] idArr = id.toCharArray();
//        if (Character.isAlphabetic(idArr[0])) {
//            return nieValidator(id);
//        } else {
//            return dniValidator(id);
//        }
//    }

//    public static User validateUser(String name,String lastName,String email,String birthDate,String dnie,String school,String course,String pass, Map<String, String> errMap) {
//        boolean validUser = true;
//        User u;
//        Iterator<Map.Entry<String, String>> mapIter = errMap.entrySet().iterator();
//        while(mapIter.hasNext() && validUser) {
//            Map.Entry<String, String> entry = mapIter.next();
//            if(entry.getValue() != null)
//                validUser = false;
//        }
//        if(validUser)
//            u = new User(name, lastName, email, birthDate, dnie, school, course, hashPassword(pass));
//        else
//            u = null;
//        return u;
//    }


//    public static String hashPassword(String pass) {
//        return BCrypt.hashpw(pass, BCrypt.gensalt());
//    }

}
