package modelsDAO;

import connections.Conector;
import models.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    /**
     * Método utilizado para crear un Course a partir de un ID
     * @author Ricardo
     * @param idCourse
     * @return Course correspondiente al ID introducido por parámetro
     */
    public static Course createCourse(int idCourse){
        Course course = new Course();
        Conector conector = new Conector();
        Connection con = null;

        try {
            con = conector.getMySqlConnection();
            if (con != null) {
                PreparedStatement sentencia = con.prepareStatement("SELECT * FROM course where id=?");
                sentencia.setInt(1, idCourse);
                ResultSet rs = sentencia.executeQuery();
                if (rs.next()) {
                    course.setId_course(idCourse);
                    course.setNameCourse(rs.getString(2));
                    course.setAcronym(rs.getString(3));
                    course.setCourseDescription(rs.getString(4));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return course;
    }

    /**
     * Método utilizado para mostrar los cursos en el registro
     * @author Ricardo
     * @return	List con los Courses
     */
    public static List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        Connection con = null;

        try {
            con = new Conector().getMySqlConnection();
            try {
                PreparedStatement ps = con.prepareStatement("select * from course;");
                ResultSet result = ps.executeQuery();
                while (result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("course_name");
                    String acronym = result.getString("acronime");
                    String courseDescription = result.getString("description_course");
                    courses.add(new Course(id, name, acronym, courseDescription));
                }
            } catch (Exception e) {
                e.printStackTrace();
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

        return courses;
    }
}
