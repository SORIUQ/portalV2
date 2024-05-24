package modelsDAO;

import connections.Conector;
import jakarta.servlet.http.HttpSession;
import models.Appointment;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
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
                    appointment.setStudentID((Integer)rs.getObject("student_id"));
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

    public static List<Appointment> getAppointmentsByDay(List<Appointment> appointments, char dayChar) {
        List<Appointment> res = new ArrayList<>();
        for(Appointment a : appointments) {
            if(a.getId().charAt(0) == dayChar) {
                res.add(a);
            }
        }
        return res;
    }

    public static List<Appointment> getAppointmentsByHour(List<Appointment> appointments, String hour) {
        List<Appointment> res = new ArrayList<>();
        for(Appointment a : appointments) {
            if(a.getTime().equals(hour)) {
                res.add(a);
            }
        }
        return res;
    }

    public static List<Appointment> getAppointments1700(List<Appointment> appointments) {
        return getAppointmentsByHour(appointments, "17:00");
    }

    public static List<Appointment> getAppointments1800(List<Appointment> appointments) {
        return getAppointmentsByHour(appointments, "18:00");
    }

    public static List<Appointment> getAppointments1900(List<Appointment> appointments) {
        return getAppointmentsByHour(appointments, "19:00");
    }

    public static List<Appointment> getMondayAppointments(List<Appointment> appointments) {
        return getAppointmentsByDay(appointments, 'L');
    }

    public static List<Appointment> getTuesdayAppointments(List<Appointment> appointments) {
        return getAppointmentsByDay(appointments, 'M');
    }


    public static List<Appointment> getWednesdayAppointments(List<Appointment> appointments) {
        return getAppointmentsByDay(appointments, 'X');
    }


    public static List<Appointment> getThursdayAppointments(List<Appointment> appointments) {
        return getAppointmentsByDay(appointments, 'J');
    }


    public static List<Appointment> getFridayAppointments(List<Appointment> appointments) {
        return getAppointmentsByDay(appointments, 'V');
    }

    public static boolean updateAppointment(String id, int student_id) {
        boolean updated = false;
        Connection con = null;

        try {
            con = new Conector().getMySqlConnection();
            PreparedStatement stmt = con.prepareStatement("UPDATE appointment set student_id = ? WHERE id = ?");
            stmt.setInt(1, student_id);
            stmt.setString(2, id);
            int results = stmt.executeUpdate();
            if (results == 1) {
                updated = true;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.getStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e){
                    e.getStackTrace();
                }
            }
        }

        return updated;
    }

    public static void appointmentUpdateMsg(String hourID, int student_id, HttpSession ses){
        if (updateAppointment(hourID,student_id)) {
            ses.setAttribute("apmOk","Se ha hecho la reserva con éxito");
            ses.setAttribute("apmError",null);
        } else {
            ses.setAttribute("apmError","No se ha podido realizar la reserva");
            ses.setAttribute("apmOk",null);

        }
    }
}
