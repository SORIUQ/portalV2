package modelsDAO;

import connections.Conector;
import jakarta.servlet.http.HttpSession;
import models.Grade;

import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GradeDAO {

    /**
     * Método utilizado para recuperar el nombre de una asignatura según su id.
     *
     * @param subject_id Id de la asignatura.
     * @return String Nombre de la asignatura.
     * @author Jose
     */
    public static String getSubjectNameFromId(int subject_id) {
        String name = null;
        Connection con = null;

        try {
            con = new Conector().getMySqlConnection();
            PreparedStatement getName = con.prepareStatement("Select subject_name from _subject where id = ?;");
            getName.setInt(1, subject_id);
            ResultSet rs = getName.executeQuery();
            if (rs.next()) {
                name = rs.getString(1);
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

        return name;
    }

    /**
     * Método que se utiliza en calificacionesAlumno para recuperar las notas de un alumno de una asignatura.
     *
     * @param subject_id
     * @param student
     * @return
     * @author Ricardo
     */

    public static List<Grade> getGradesFromSubjectId(int subject_id, User student) {
        List<Grade> grades = new ArrayList<>();
        Connection con = null;

        try {
            con = new Conector().getMySqlConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM grades WHERE subject_id = ? and student = ?;");
            ps.setInt(1, subject_id);
            ps.setInt(2, student.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Grade grade = new Grade();
                grade.setSubject_id(rs.getInt("subject_id"));
                grade.setGrade(rs.getBigDecimal("grade"));
                grade.setTeacher_id(rs.getInt("teacher"));
                grade.setStudent_id(rs.getInt("student"));
                grade.setGrade_desc(rs.getString("grade_description"));
                if (grade.getGrade() != null) {
                    grades.add(grade);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return grades;
    }

    /**
     * Sobrecarga del método
     *
     * @param subject_id
     * @param student_id
     * @return
     * @author Jose
     */
    public static List<Grade> getGradesFromSubjectId(int subject_id, int student_id) {
        List<Grade> grades = new ArrayList<>();
        Connection con = null;

        try {
            con = new Conector().getMySqlConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM grades WHERE subject_id = ? and student = ?;");
            ps.setInt(1, subject_id);
            ps.setInt(2, student_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Grade grade = new Grade();
                grade.setSubject_id(rs.getInt("subject_id"));
                grade.setGrade(rs.getBigDecimal("grade"));
                grade.setTeacher_id(rs.getInt("teacher"));
                grade.setStudent_id(rs.getInt("student"));
                grade.setGrade_desc(rs.getString("grade_description"));
                if (grade.getGrade() != null) {
                    grades.add(grade);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return grades;
    }

    // POR TERMINAR
    public static void updateGrade(HttpSession ses, int teacher_id, int student_id, double grade, String description) {
        Connection con = null;

        try {
            con = new Conector().getMySqlConnection();
            if (con != null) {
                PreparedStatement ps = con.prepareStatement("UPDATE grades set grade = ? WHERE ");
                int result = ps.executeUpdate();
                if (result != 0) {
                    ses.setAttribute("updateGradeError",null);
                    ses.setAttribute("updateGradeOk", "Se ha actualizado la nota correctamente");
                } else {
                    ses.setAttribute("updateGradeError","No se ha podido actualizar la nota");
                    ses.setAttribute("updateGradeOk", null);
                }

            } else {
                ses.setAttribute("updateGradeError","No se ha podido conectar con la Base de Datos");
                ses.setAttribute("updateGradeOk", null);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void addGrade(HttpSession ses, int subject_id, int teacher_id, int student_id, double grade, String description){
        Connection con = null;

        try{
            con = new Conector().getMySqlConnection();
            if (con != null) {
                PreparedStatement ps = con.prepareStatement("INSERT INTO grades values (?,?,?,?,?)");
                ps.setInt(1,teacher_id);
                ps.setInt(2,student_id);
                ps.setInt(3,subject_id);
                ps.setString(4,description);
                ps.setDouble(5,grade);
                int result = ps.executeUpdate();
                if (result != 0) {
                    ses.setAttribute("insertGradeError",null);
                    ses.setAttribute("insertGradeOk", "Nota añadida con éxito");
                } else {
                    ses.setAttribute("insertGradeError","No se ha podido añadir la nota");
                    ses.setAttribute("insertGradeOk", null);
                }

            } else {
                ses.setAttribute("insertGradeError","No se ha podido conectar con la Base de Datos");
                ses.setAttribute("insertGradeOk", null);
            }

        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteGrade(int teacher_id, int student_id, int subject_id, String description, double grade) {
        Connection con = null;

        try {
            con = new Conector().getMySqlConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM grades WHERE teacher = ? and student = ? and subject_id = ? and grade_description = ? and grade = ?;");
            ps.setInt(1,teacher_id);
            ps.setInt(2,student_id);
            ps.setInt(3,subject_id);
            ps.setString(4,description);
            ps.setDouble(5,grade);
            ps.executeUpdate();

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
    }
}