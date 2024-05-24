package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Appointment;
import modelsDAO.AppointmentDAO;
import models.User;


import java.io.IOException;
import java.util.List;

@WebServlet(name = "AppointmentServlet", urlPatterns = "/appointments")
public class AppointmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession ses = req.getSession();
        String id = req.getParameter("selectedTeacherID");
        if (id != null && !id.equals(ses.getAttribute("selectedTeacherID")))
            ses.setAttribute("selectedTeacherID", id);
        List<Appointment> appointments = AppointmentDAO.getAppointments(Integer.parseInt((String)ses.getAttribute("selectedTeacherID")));
        ses.setAttribute("appointments", appointments);
        resp.sendRedirect("./jsp/tutoria.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String hourID = req.getParameter("hourSelectedID");
        HttpSession ses = req.getSession();
        int student_id = ((User) ses.getAttribute("user")).getId();
        AppointmentDAO.appointmentUpdateMsg(hourID, student_id, ses);
        List<Appointment> appointments = AppointmentDAO.getAppointments(Integer.parseInt((String)ses.getAttribute("selectedTeacherID")));
        ses.setAttribute("appointments", appointments);
        doGet(req,resp);
    }
}
