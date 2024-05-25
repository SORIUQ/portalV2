package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Appointment;
import modelsDAO.AppointmentDAO;
import models.User;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AppointmentServlet", urlPatterns = "/appointments")
public class AppointmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("selectedTeacherID");
        HttpSession ses = req.getSession();
        if (id != null && !id.equals(ses.getAttribute("selectedTeacherID")))
            ses.setAttribute("selectedTeacherID", id);
        List<Appointment> appointments = AppointmentDAO.getAppointments(Integer.parseInt((String)ses.getAttribute("selectedTeacherID")));

        ses.setAttribute("appointments", appointments);
        resp.sendRedirect("./jsp/reservas.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String hourID = req.getParameter("hourSelectedID");
        HttpSession ses = req.getSession();
        int student_id = ((User) ses.getAttribute("user")).getId();
        List<Appointment> appointments = (ses.getAttribute("appointments")==null) ?
                new ArrayList<>() : (List<Appointment>) ses.getAttribute("appointments");

        AppointmentDAO.updateAppointment(ses, hourID, student_id, appointments);
        doGet(req,resp);
    }
}
