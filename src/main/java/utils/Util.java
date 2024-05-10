package utils;

import connections.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

import models.*;


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
            user.setId_course((Integer) resultSet.getObject("course_id"));
        }
        return user;
    }

	public static String getContentTarjetaIndex(User activeUser, String centroUsuario, Course crs) {
		String result = "";
		switch (activeUser.getUserType()) {
			case "01" -> {
				result = "Alumno de " + crs.getNameCourse();
			}
			case "02" -> {
				result = "Profesor " + centroUsuario;
			}
			case "03" -> {
				result = "Empleado/a de Accenture";
			}
		}
		return result;
	}
    public static String defineImage(int id){
    	String imagen="";

    	switch(id){
    	case 1->{
    		imagen="../images/logos/LOGOTIPO-CESUR.png";
    	}
    	case 2->{
    		imagen="../images/logos/LOGOTIPO-IES-PABLO-PICASSO.png";
    	}
    	case 3->{
    		imagen="../images/logos/LOGOTIPO-IES-BELEN.png";
    	}
    	case 4->{
    		imagen="../images/logos/LOGOTIPO-ALAN-TURING.png";
    	}
    	case 5->{
    		imagen="../images/logos/LOGOTIPO-IES-SAN-JOSE.png";
    	}
    	}
    	return imagen;
    }

    public static String defineImageIndex(Integer id){
    	String imagen="";

    	switch(id) {
			case 1 -> {
				imagen = "./images/logos/LOGOTIPO-CESUR.png";
			}
			case 2 -> {
				imagen = "./images/logos/LOGOTIPO-IES-PABLO-PICASSO.png";
			}
			case 3 -> {
				imagen = "./images/logos/LOGOTIPO-IES-BELEN.png";
			}
			case 4 -> {
				imagen = "./images/logos/LOGOTIPO-ALAN-TURING.png";
			}
			case 5 -> {
				imagen = "./images/logos/LOGOTIPO-IES-SAN-JOSE.png";
			}
			default -> {
				imagen = "./images/logos/LOGOTIPO-ACCENTURE.png";
			}
		}
    	return imagen;
    }
    
    public static School getInfoSchool(int idSchool){

		int idSchoolConstr=idSchool;
		String nombreSchool="";
		String tlfSchool= "";
		String email= "";
		String scheduleSchool= "";
		String locSchool="";

		Conector conector = new Conector();

		try(Connection conx = conector.getMySqlConnection()){

			if (conx != null) {
				String sql = "SELECT * FROM school where id="+idSchool;
				Statement sentencia = conx.createStatement();

				try (ResultSet rs = sentencia.executeQuery(sql)) {
					while (rs.next()) {
						idSchoolConstr=rs.getInt(1);
						nombreSchool=rs.getString(2);
						tlfSchool= rs.getString(3);
						email= rs.getString(4);
						scheduleSchool= rs.getString(5);
						locSchool=rs.getString(6);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new School(idSchoolConstr, nombreSchool, tlfSchool, email, scheduleSchool, locSchool);
	}
    
    public static Course getCourseInfo(int idCourse){

		int idCourseConstr=idCourse;
		String nameCourse="";

		Conector conector = new Conector();

		try(Connection conx = conector.getMySqlConnection()){

			if (conx != null) {
				System.out.println("Conexión OK");

				String sql = "SELECT * FROM course where id="+idCourse;
				Statement sentencia = conx.createStatement();

				try (ResultSet rs = sentencia.executeQuery(sql)) {

					while (rs.next()) {
						nameCourse=rs.getString(2);
					}

				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Course(idCourseConstr, nameCourse);
	}

	public static void indexGetValues(HttpServletRequest request, HttpServletResponse response, HttpSession session,
							   Integer idSchool, String imagen, School sch, User activeUser, String contentTarjeta,
							   String courseName, Course crs, String centroUsuario) {

			System.out.println("entro a dar valores");
			System.out.println(activeUser);
			idSchool = activeUser.getId_school();
			System.out.println(idSchool);
			imagen = Util.defineImageIndex(idSchool);
			sch = Util.getInfoSchool(idSchool);
			crs = Util.getCourseInfo(activeUser.getId_course());
			System.out.println(crs);
			centroUsuario = sch.getNombreSchool();
			System.out.println(centroUsuario);

			switch (activeUser.getUserType()) {
				case "01": {
					contentTarjeta = "Alumno de " + crs.getNameCourse();
					break;

				}
				case "02": {
					contentTarjeta = "Profesor " + centroUsuario;
					break;

				}
				case "03": {
					contentTarjeta = "Empleado/a de Accenture";
					centroUsuario = "Accenture";
					imagen = "./images/logos/LOGOTIPO-ACCENTURE.png";
					break;
				}
			}
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
