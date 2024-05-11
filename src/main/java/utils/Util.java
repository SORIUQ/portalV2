package utils;

import connections.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.*;

import models.*;


public class Util {

    public static boolean loginProcess(HttpServletRequest req, HttpSession session) {
        boolean logged = false;
        Conector conector = new Conector();
        Connection con = null;
        try {
            con = conector.getMySqlConnection();
			if (con != null) {
				String email = req.getParameter("email");
				String password = req.getParameter("password");
				PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM credentials WHERE email = '" + email + "'");
				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next() && resultSet.getString("pass").equals(password)) {
					User user = createUserLogin(con, resultSet);
					session.setAttribute("user", user);
					logged = true;
				}
			} else
				System.out.println("No se ha podido establecer la conexión");
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
        return logged;
    }

    public static User createUserLogin(Connection con, ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        Statement preparedStatement = con.createStatement();
        resultSet = preparedStatement.executeQuery("SELECT * FROM user_obj WHERE id = " + user.getId());
        if (resultSet.next()) {
            user.setUserType(resultSet.getString("user_type"));
            user.setName(resultSet.getString("user_name"));
            user.setId_school(resultSet.getInt("school_id"));
            user.setId_course((Integer) resultSet.getObject("course_id"));
        }
        return user;
    }

	public static String getContentTarjetaIndex(User activeUser, String centroUsuario, Course crs) {
		String result = "";
		switch (activeUser.getUserType()) {
			case "01" -> {result = "Alumno de " + crs.getNameCourse();}
			case "02" -> {result = "Profesor " + centroUsuario;}
			case "03" -> {result = "Empleado/a de Accenture";}
		}
		return result;
	}

    public static String defineImageIndex(int id){
    	String imagen="";
    	switch(id) {
			case 1->{imagen="./images/logos/LOGOTIPO-CESUR.png";}
			case 2->{imagen="./images/logos/LOGOTIPO-IES-PABLO-PICASSO.png";}
			case 3->{imagen="./images/logos/LOGOTIPO-IES-BELEN.png";}
			case 4->{imagen="./images/logos/LOGOTIPO-ALAN-TURING.png";}
			case 5->{imagen="./images/logos/LOGOTIPO-IES-SAN-JOSE.png";}
    	}
    	return imagen;
    }
    
    public static School getInfoSchool(int idSchool){

		School school = new School();
		school.setIdSchool(idSchool);
		Conector conector = new Conector();
		Connection con = null;
		try {
			con = conector.getMySqlConnection();
			if (con != null) {
				String sql = "SELECT * FROM school where id = " + idSchool;
				Statement statement = con.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				if (rs.next()) {
					school.setNameSchool(rs.getString(2));
					school.setTlfSchool(rs.getString(3));
					school.setEmail(rs.getString(4));
					school.setScheduleSchool(rs.getString(5));
					school.setLocSchool(rs.getString(6));
				}
			} else
				System.out.println("No se ha podido establecer la conexión");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
		return school;
	}
    
    public static Course getCourseInfo(int idCourse){

		Course course = new Course();
		course.setId_course(idCourse);
		Conector conector = new Conector();
		Connection con = null;
		try {
			con = conector.getMySqlConnection();
			if (con != null) {
				Statement sentencia = con.createStatement();
				ResultSet rs = sentencia.executeQuery("SELECT * FROM course where id = " + idCourse);
				if (rs.next()) {
					course.setNameCourse(rs.getString(2));
				}
			} else
				System.out.println("No se ha podido establecer la conexión");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
		return course;
	}
    
    public static String defineMap(int idSchool) {
    	String mapLink="";
    	switch(idSchool){
			case 1 -> {mapLink="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d10757.374130614628!2d-4.372041717464043!3d36.71808277803187!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd7259120bfc4db3%3A0xec0ecedd8dc61902!2sCESUR%20M%C3%A1laga%20Este%20Formaci%C3%B3n%20Profesional!5e0!3m2!1ses!2ses!4v1715334512514!5m2!1ses!2ses";}
			case 2 -> {mapLink="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d6395.717928363966!2d-4.455162806420868!3d36.725948300000006!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd72f70c3d574e37%3A0x67343146876c734b!2sIES%20Pablo%20Picasso!5e0!3m2!1ses!2ses!4v1715335018709!5m2!1ses!2ses";}
			case 3 -> {mapLink="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3199.124902411609!2d-4.459761523439527!3d36.69553637227712!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd72f9dee2ea3131%3A0xe00a7d745fb8b2e3!2sIES%20Bel%C3%A9n!5e0!3m2!1ses!2ses!4v1715335056310!5m2!1ses!2ses";}
			case 4 -> {mapLink="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3197.2394272377824!2d-4.554430616275409!3d36.740823696739334!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd72f10963ce0f3d%3A0x310ae7d4bb2e8f7b!2sCPIFP%20Alan%20Turing!5e0!3m2!1ses!2ses!4v1715335096355!5m2!1ses!2ses";}
			case 5 -> {mapLink="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d60854.997797710945!2d-4.459410649332534!3d36.715431468654785!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd72f711c56e8bed%3A0x6de2361e88593aeb!2sColegio%20Diocesano%20San%20Jos%C3%A9%20Obrero!5e0!3m2!1ses!2ses!4v1715335137121!5m2!1ses!2ses";}
    	}
    	return mapLink;
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
