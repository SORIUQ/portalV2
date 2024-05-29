package modelsDAO;

import connections.Conector;
import jakarta.servlet.http.HttpSession;
import models.Appointment;

import java.sql.*;
import java.util.*;

public class AppointmentDAO {

    /**
     * Método que se utiliza para rescatar la lista de tutorías de un profesor concreto.
     * @author Ricardo
     * @param id Id del profesor
     * @return appointments Lista con las tutorías del profesor.
     */
    public static List<Appointment> getAppointments(int id) {
        Connection con = null;
        List<Appointment> appointments = new ArrayList<>();

        try {
            con = new Conector().getMySqlConnection();
            if (con != null) {
                // No hace falta realizar una PreparedStatement porque el ID que recogemos no viene dado por el usuario, viene de la base de datos directamente
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from appointment WHERE teacher_id = " + id);
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
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return appointments;
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

    /**
     * Método que se utiliza para reservar una tutoría con un profesor.
     * Si el estudiante ya tiene una hora reservada, no se le permitirá reservar otra.
     * @author Ricardo y Jose
     * @param ses Sesión recogida en AppointmentServlet.
     * @param appointment_id Id de la hora seleccionada en la página de tutorías.
     * @param appointments Lista con las tutorías del profesor seleccionado.
     */
    public static void updateAppointment(HttpSession ses, String appointment_id, int student_id, List<Appointment> appointments) {
        Connection con = null;

        boolean alreadyAppointed = false;
        int i = 0;
        while(i < appointments.size() && !alreadyAppointed) {
            if(appointments.get(i).getStudentID() != null && appointments.get(i).getStudentID().equals(student_id))
                alreadyAppointed = true;
            i++;
        }

        if (!alreadyAppointed) {
            try {
                con = new Conector().getMySqlConnection();
                PreparedStatement stmt = con.prepareStatement("UPDATE appointment set student_id = ? WHERE id = ?");
                stmt.setInt(1, student_id);
                stmt.setString(2, appointment_id);
                int results = stmt.executeUpdate();
                if (results != 0) {
                    ses.setAttribute("appointmentOk", "Se ha hecho la reserva con éxito");
                    ses.setAttribute("appointmentError", null);
                } else {
                    ses.setAttribute("appointmentError","No se ha podido realizar la reserva");
                    ses.setAttribute("appointmentOk",null);
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
            ses.setAttribute("appointmentError","Ya tienes una reserva hecha");
            ses.setAttribute("appointmentOk",null);
        }
    }

    /**
     * Método que se utiliza para cancelar una reserva con un profesor.
     * Si el estudiante no tiene ninguna hora reservada no podrá cancelar nada.
     * @author Ricardo y Jose
     * @param ses Sesión recogida en DeleteAppointmentServlet.
     * @param student_id Id del estudiante que quiere cancelar la tutoría.
     * @param appointments Lista con las tutorías del profesor seleccionado.
     */
    public static void deleteAppointment(HttpSession ses, int student_id, List<Appointment> appointments) {
        ses.setAttribute("errorMsg","Error al borrar las tutoría reservada");
        Connection con;
        int teacher_id = 0;

        boolean exists = false;
        for (int i = 0; i < appointments.size() && !exists; i++) {
            if (appointments.get(i).getStudentID() != null && appointments.get(i).getStudentID().equals(student_id)) {
                exists = true;
                teacher_id = appointments.get(i).getTeacherID();
            }
        }

        if (exists) {
            try {
                con = new Conector().getMySqlConnection();
                PreparedStatement ps = con.prepareStatement("UPDATE appointment set student_id = null WHERE student_id = ? and teacher_id = ?");
                ps.setInt(1,student_id);
                ps.setInt(2,teacher_id);
                int result = ps.executeUpdate();
                if (result != 0) {
                    ses.setAttribute("okMsg","Se ha cancelado la tutoría reservada");
                    ses.setAttribute("errorMsg",null);
                } else {
                    ses.setAttribute("okMsg",null);
                    ses.setAttribute("errorMsg","No se ha podido cancelar la tutoría");
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            ses.setAttribute("okMsg",null);
            ses.setAttribute("errorMsg","No hay ninguna tutoría que cancelar");
        }
    }

    public static HashMap<String,Object> getAppointmentInfo(String appointment_id) {
        Connection con = null;
        HashMap<String,Object> appointmentInfo = new HashMap<>();

        try {
            con = new Conector().getMySqlConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM appointment AS ap INNER JOIN user_obj AS u ON ap.student_id = u.id WHERE ap.id = ?");
            ps.setString(1, appointment_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                appointmentInfo.put("studentName", rs.getString("u.user_name"));
                appointmentInfo.put("studentSurname", rs.getString("u.user_surname"));
                appointmentInfo.put("time", rs.getString("ap.hour"));
                appointmentInfo.put("date", rs.getString("ap.day"));
                appointmentInfo.put("room", rs.getString("ap.room"));
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

        return appointmentInfo;
    }
}
