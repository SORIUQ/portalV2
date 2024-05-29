package modelsDAO;

import connections.Conector;
import models.Subject;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    /**
     * Método para almacenar en una lista todas las asignaturas relacionadas con el curso en cuestión.
     * @Author Alberto G.
     * @param courseId id del curso del usuario activo
     * @return List<Subject>
     */
    public static List<Subject> getAllSubjectsByCourseId(Integer courseId) {
        List<Subject> subjects = new ArrayList<>();
        Connection con = null;

        try {
            con = new Conector().getMySqlConnection();
            PreparedStatement ps = con.prepareStatement("Select * from _subject as s inner join course_subject as cs on s.id = cs.subject_id where cs.course_id = ?;");
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) { // Column 1: id, Column 2: subject_name , Column 3: weekly_hours , Column 4: total_hours
                subjects.add(new Subject(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
            }

        } catch (SQLException | ClassNotFoundException e) {
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

        return subjects;
    }

    /**
     * Método para seleccionar una Asignatura en específico mediante su id
     * @Author Alberto G.
     * @param id
     * @return Objeto de tipo Subject
     */
    public static Subject getSubjectByID(int id) {
        Subject s = new Subject();
        try (Connection conn = new Conector().getMySqlConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("Select * from _subject where id = ?")) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        s.setSubjectId(rs.getInt("id"));
                        s.setName(rs.getString("subject_name"));
                        s.setWeeklyHours(rs.getInt("weekly_hours"));
                        s.setTotalHours(rs.getInt("total_hours"));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * Método que se utiliza en calificaciones.jsp para sacar las asignaturas de un estudiante.
     * Carga solamente los datos necesarios para crear el select de las asignaturas.
     * @author Ricardo
     * @param student Usuario del estudiante.
     * @return List<Subject> Lista de las asignaturas.
     */
    public static List<Subject> getSubjectsFromStudentGrades(User student) {
        List<Subject> subjects = new ArrayList<>();
        Connection con = null;

        try {
            con = new Conector().getMySqlConnection();
            PreparedStatement ps = con.prepareStatement("SELECT su.id,su.subject_name FROM course_subject AS cs INNER JOIN _subject as su on cs.subject_id = su.id WHERE cs.course_id = ?");
            ps.setInt(1,student.getCourse_id());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt(1));
                subject.setName(rs.getString(2));
                subjects.add(subject);
            }

        } catch (SQLException | ClassNotFoundException e) {
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

        return subjects;
    }
}
