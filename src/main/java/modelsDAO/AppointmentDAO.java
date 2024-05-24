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

//    public static List<Appointment> getAppointmentsByDay(List<Appointment> appointments, char dayChar) {
//        List<Appointment> res = new ArrayList<>();
//        for(Appointment a : appointments) {
//            if(a.getId().charAt(0) == dayChar) {
//                res.add(a);
//            }
//        }
//        return res;
//    }

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

//    public static List<Appointment> getMondayAppointments(List<Appointment> appointments) {
//        return getAppointmentsByDay(appointments, 'L');
//    }
//
//    public static List<Appointment> getTuesdayAppointments(List<Appointment> appointments) {
//        return getAppointmentsByDay(appointments, 'M');
//    }
//
//
//    public static List<Appointment> getWednesdayAppointments(List<Appointment> appointments) {
//        return getAppointmentsByDay(appointments, 'X');
//    }
//
//
//    public static List<Appointment> getThursdayAppointments(List<Appointment> appointments) {
//        return getAppointmentsByDay(appointments, 'J');
//    }
//
//
//    public static List<Appointment> getFridayAppointments(List<Appointment> appointments) {
//        return getAppointmentsByDay(appointments, 'V');
//    }

    public static boolean updateAppointment(HttpSession ses, String id, int student_id, List<Appointment> appointments) {
        boolean updated = false;
        Connection con = null;

        boolean alreadyAppointed = false;
        for (Appointment a : appointments) {
            if (a.getStudentID() != null && a.getStudentID().equals(student_id)) {
                alreadyAppointed = true;
                break;
            }
        }

        if (!alreadyAppointed) {
            try {
                con = new Conector().getMySqlConnection();
                PreparedStatement stmt = con.prepareStatement("UPDATE appointment set student_id = ? WHERE id = ?");
                stmt.setInt(1, student_id);
                stmt.setString(2, id);
                int results = stmt.executeUpdate();
                if (results != 0) {
                    updated = true;
                    ses.setAttribute("okMsg", "Se ha hecho la reserva con éxito");
                    ses.setAttribute("errorMsg", null);
                } else {
                    ses.setAttribute("errorMsg","No se ha podido realizar la reserva");
                    ses.setAttribute("okMsg",null);
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.getStackTrace();
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.getStackTrace();
                    }
                }
            }
        } else {
            ses.setAttribute("errorMsg","Ya tienes una reserva hecha");
            ses.setAttribute("okMsg",null);
        }

        return updated;
    }

//    public static void appointmentUpdateMsg(HttpSession ses, String hourID, int student_id, List<Appointment> appointments){
//        if (updateAppointment(hourID,student_id, appointments)) {
//            ses.setAttribute("apmOk","Se ha hecho la reserva con éxito");
//            ses.setAttribute("apmError",null);
//        } else {
//            ses.setAttribute("apmError","No se ha podido realizar la reserva");
//            ses.setAttribute("apmOk",null);
//        }
//    }

//    public static boolean deleteAppointment(int student_id, String teacherID){
//        boolean deleted = false;
//        Connection con = null;
//
//        try {
//            con = new Conector().getMySqlConnection();
//            PreparedStatement stmt = con.prepareStatement("UPDATE appointment set student_id = null WHERE student_id = ? and teacher_id = ?");
//            stmt.setInt(1,student_id);
//            stmt.setString(2,teacherID);
//            int results = stmt.executeUpdate();
//            if (results != 0)
//                deleted = true;
//
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e){
//                    e.getStackTrace();
//                }
//            }
//        }
//
//        return deleted;
//    }

    public static void deleteAppointment(HttpSession ses, int student_id, List<Appointment> appointments) {
        Connection con = null;
        ses.setAttribute("errorMsg","Error al borrar las tutoría reservada");

        boolean exists = false;
        for (Appointment a : appointments) {
            if (a.getStudentID() != null && a.getStudentID().equals(student_id)) {
                exists = true;
                break;
            }
        }

        if (exists) {
            try {
                con = new Conector().getMySqlConnection();
                PreparedStatement ps = con.prepareStatement("UPDATE appointment set student_id = null WHERE student_id = ?");
                ps.setInt(1,student_id);
                int result = ps.executeUpdate();
                if (result != 0) {
                    ses.setAttribute("okMsg","Se ha borrado la tutoría reservadas");
                    ses.setAttribute("errorMsg",null);
                } else {
                    ses.setAttribute("okMsg",null);
                    ses.setAttribute("errorMsg","No se ha podidio borrar la tutoría");
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            ses.setAttribute("okMsg",null);
            ses.setAttribute("errorMsg","No hay ninguna tutoría que cancelar");
        }
    }

//    public static void deleteAppointmentMsg(HttpSession ses, int student_id, String teacher_id){
//        if (deleteAppointment(student_id, teacher_id)){
//            ses.setAttribute("deleteMsg","Se han borrado todas las tutorias reservadas");
//            ses.setAttribute("okMsg",null);
//            ses.setAttribute("errorMsg",null);
//        } else ses.setAttribute("deleteMsg","Error al borrar las tutorias reservadas");
//    }
}
