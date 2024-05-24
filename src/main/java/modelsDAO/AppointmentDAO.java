package modelsDAO;

import connections.Conector;
import models.Appointment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class AppointmentDAO {


    public static List<Appointment> getAppointments(int id) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Appointment> appointments = new ArrayList<>();

        try {
            con = new Conector().getMySqlConnection();
            if (con != null) {
                stmt = con.createStatement();
                // No hace falta realizar una PreparedStatement porque el ID que recogemos no viene dado por el usuario, viene de la base de datos directamente
                rs = stmt.executeQuery("select * from appointment WHERE teacher_id = " + id);
                while (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setId(rs.getString("id"));
                    appointment.setTeacherID(rs.getInt("teacher_id"));
                    appointment.setStudentID(rs.getInt("student_id"));
                    appointment.setDate(rs.getString("day"));
                    appointment.setTime(rs.getString("hour"));
                    appointments.add(appointment);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    public static void updateReservation(int id, String day, String time) {

    }
}
