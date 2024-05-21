package modelsDAO;

import connections.Conector;
import models.Subject;

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
        try (Connection conn = new Conector().getMySqlConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("Select * from _subject;")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) { // Column 1: id, Column 2: subject_name , Column 3: weekly_hours , Column 4: total_hours
                        subjects.add(new Subject(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
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
}
