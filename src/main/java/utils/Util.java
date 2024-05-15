package utils;

import connections.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    
    public static String defineID(int id){
    	String nombre="";

    	switch(id){
    	case 1->{
    		nombre="./jsp/noticiasCesur.jsp";
    	}
    	case 2->{
    		nombre="./jsp/noticiasPabloPicasso.jsp";
    	}
    	case 3->{
    		nombre="./jsp/noticiasBelen.jsp";
    	}
    	case 4->{
    		nombre="./jsp/noticiasAlanTuring.jsp";
    	}
    	case 5->{
    		nombre="./jsp/noticiasSanJose.jsp";
    		}
    	}
    	
    	return nombre;
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
    
    public static String defineMap(int idSchool) {
    	String mapLink="";
    	
    	switch(idSchool){
    	case 1->{
    		mapLink="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d10757.374130614628!2d-4.372041717464043!3d36.71808277803187!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd7259120bfc4db3%3A0xec0ecedd8dc61902!2sCESUR%20M%C3%A1laga%20Este%20Formaci%C3%B3n%20Profesional!5e0!3m2!1ses!2ses!4v1715334512514!5m2!1ses!2ses";
;
    	}
    	case 2->{
    		mapLink="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d6395.717928363966!2d-4.455162806420868!3d36.725948300000006!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd72f70c3d574e37%3A0x67343146876c734b!2sIES%20Pablo%20Picasso!5e0!3m2!1ses!2ses!4v1715335018709!5m2!1ses!2ses";
    	}
    	case 3->{
    		mapLink="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3199.124902411609!2d-4.459761523439527!3d36.69553637227712!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd72f9dee2ea3131%3A0xe00a7d745fb8b2e3!2sIES%20Bel%C3%A9n!5e0!3m2!1ses!2ses!4v1715335056310!5m2!1ses!2ses";
    	}
    	case 4->{
    		mapLink="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3197.2394272377824!2d-4.554430616275409!3d36.740823696739334!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd72f10963ce0f3d%3A0x310ae7d4bb2e8f7b!2sCPIFP%20Alan%20Turing!5e0!3m2!1ses!2ses!4v1715335096355!5m2!1ses!2ses";
    	}
    	case 5->{
    		mapLink="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d60854.997797710945!2d-4.459410649332534!3d36.715431468654785!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xd72f711c56e8bed%3A0x6de2361e88593aeb!2sColegio%20Diocesano%20San%20Jos%C3%A9%20Obrero!5e0!3m2!1ses!2ses!4v1715335137121!5m2!1ses!2ses";
    		}
    	}
    	return mapLink;
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
			centroUsuario = sch.getSchoolName();
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

	public static List<School> getAllSchools() {
		List<School> schools = new ArrayList<>();
		Connection con = null;
		try {
			con = new Conector().getMySqlConnection();
			try {
				PreparedStatement ps = con.prepareStatement("select * from school");
				ResultSet result = ps.executeQuery();
				while (result.next()) {
					int id = result.getInt("id");
					String name = result.getString("school_name");
					String telephone = result.getString("tel");
					String email = result.getString("email");
					String secretarySchedule = result.getString("secretarySchedule");
					String location = result.getString("loc");
					schools.add(new School(id, name, telephone, email, secretarySchedule, location));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if(con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return schools;
	}

	public static List<Course> getAllCourses() {
		List<Course> courses = new ArrayList<>();
		Connection con = null;
		try {
			con = new Conector().getMySqlConnection();
			try {
				PreparedStatement ps = con.prepareStatement("select id, course_name from course;");
				ResultSet result = ps.executeQuery();
				while (result.next()) {
					int id = result.getInt("id");
					String name = result.getString("course_name");
					courses.add(new Course(id, name));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if(con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return courses;
	}
}
