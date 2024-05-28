package modelsDAO;

import connections.Conector;
import models.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//    /**
//     * Método utilizado para mostrar los cursos en el registro
//     * @author Ricardo
//     * @return	List con los Courses
//     */
//    public static List<Course> getAllCourses() {
//        List<Course> courses = new ArrayList<>();
//        Connection con = null;
//
//        try {
//            con = new Conector().getMySqlConnection();
//            try {
//                PreparedStatement ps = con.prepareStatement("select * from course;");
//                ResultSet result = ps.executeQuery();
//                while (result.next()) {
//                    int id = result.getInt("id");
//                    String name = result.getString("course_name");
//                    String acronym = result.getString("acronime");
//                    String courseDescription = result.getString("description_course");
//                    courses.add(new Course(id, name, acronym, courseDescription));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return courses;
//    }

    public static Map<Integer, List<Course>> getAllCoursesOfSchool() {
        Map<Integer, List<Course>> coursesMap = new HashMap<>();
        Connection conn = null;
        try {
            conn = new Conector().getMySqlConnection();
            try (PreparedStatement ps = conn.prepareStatement("Select sc.school_id, c.* From school_course as sc inner join course as c on sc.course_id = c.id")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Course c = new Course();
                        int school_id = rs.getInt("school_id");
                        c.setId_course(rs.getInt("id"));
                        c.setNameCourse(rs.getString("course_name"));
                        c.setAcronym(rs.getString("acronime"));
                        c.setCourseDescription(rs.getString("description_course"));

                        List<Course> schoolCourses = coursesMap.getOrDefault(school_id, new ArrayList<>());
                        schoolCourses.add(c);
                        coursesMap.put(school_id, schoolCourses);
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return coursesMap;
    }
}
