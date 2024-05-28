package modelsDAO;

import connections.Conector;
import models.Grade;
import models.Subject;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GradeDAO {

    /**
     * Método utilizado para recuperar el nombre de una asignatura según su id.
     * @author Jose
     * @param subject_id Id de la asignatura.
     * @return String Nombre de la asignatura.
     */
    public static String getSubjectNameFromId (int subject_id){
        String name = null;
        Connection con = null;

        try {
            con = new Conector().getMySqlConnection();
            PreparedStatement getName = con.prepareStatement("Select subject_name from _subject where id = ?;");
            getName.setInt(1,subject_id);
            ResultSet rs = getName.executeQuery();
            if (rs.next()) {
                name = rs.getString(1);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
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
     * Método que se utiliza en calificacionesAlumno
     * @author Ricardo
     * @param subject_id
     * @param student
     * @return
     */
    public static List<Grade> getGradesFromSubjectId(int subject_id, User student) {
        List<Grade> grades = new ArrayList<>();
        Connection con = null;

        try {
            con = new Conector().getMySqlConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM grades WHERE subject_id = ? and student = ?;");
            ps.setInt(1,subject_id);
            ps.setInt(2,student.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Grade grade = new Grade();
                grade.setSubject_id(rs.getInt("subject_id"));
                grade.setGrade((Double)rs.getObject("grade"));
                grade.setTeacher_id(rs.getInt("teacher"));
                grade.setStudent_id(rs.getInt("student"));
                if (grade.getGrade() != null) {
                    grades.add(grade);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return grades;
    }

    public static void updateGrade() {
        Connection con = null;

        try {
            con = new Conector().getMySqlConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE grades set grade = ? WHERE ");
            int result = ps.executeUpdate();
            if (result != 0){

            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
/*
DELIMITER //
	CREATE TRIGGER user_subjects
	AFTER INSERT ON user_obj
	FOR EACH ROW
	BEGIN
		DECLARE teacher_id INT;
		DECLARE subjectBuilder INT;
		DECLARE done INT DEFAULT 0;
		DECLARE done2 INT DEFAULT 0;

		DECLARE teacher_cursor CURSOR FOR
			SELECT id
			FROM user_obj
			WHERE course_id = NEW.course_id AND school_id = NEW.school_id AND user_type = "02";

		DECLARE subject_cursor CURSOR FOR
			SELECT subject_id
			FROM course_subject
			WHERE course_id = NEW.course_id;

		DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

		IF NEW.user_type = '01' THEN
			OPEN teacher_cursor;
				teacher_loop: LOOP
					FETCH teacher_cursor INTO teacher_id;
					IF done THEN
						LEAVE teacher_loop;
					END IF;

					SET done2 = 0; -- Reset the second handler flag

					BEGIN
						DECLARE CONTINUE HANDLER FOR NOT FOUND SET done2 = 1;

						OPEN subject_cursor;

						subject_loop: LOOP
							FETCH subject_cursor INTO subjectBuilder;
							IF done2 THEN
								LEAVE subject_loop;
							END IF;

							INSERT INTO GRADES (teacher, student, subject_id, grade)
							VALUES (teacher_id, NEW.id, subjectBuilder, NULL);
						END LOOP subject_loop;

						CLOSE subject_cursor;
					END;

				END LOOP teacher_loop;
			CLOSE teacher_cursor;
		END IF;
	END;
//
DELIMITER ;
 */