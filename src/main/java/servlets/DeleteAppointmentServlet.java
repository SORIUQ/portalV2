package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Appointment;
import models.User;
import modelsDAO.AppointmentDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "DeleteAppointment", urlPatterns = "/deleteAppointment")
public class DeleteAppointmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession ses = req.getSession();
        String id = req.getParameter("selectedTeacherID");
        if (id != null && !id.equals(ses.getAttribute("selectedTeacherID")))
            ses.setAttribute("selectedTeacherID", id);
        List<Appointment> appointments = AppointmentDAO.getAppointments(Integer.parseInt((String)ses.getAttribute("selectedTeacherID")));
        ses.setAttribute("appointments", appointments);
        resp.sendRedirect("./jsp/reservas.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession ses = req.getSession();
        int student_id = ((User) ses.getAttribute("user")).getId();
        List<Appointment> appointments = (List<Appointment>) ses.getAttribute("appointments");
        AppointmentDAO.deleteAppointment(ses, student_id, appointments);
        doGet(req,resp);
    }
}
