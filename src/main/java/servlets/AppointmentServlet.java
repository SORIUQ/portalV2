package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Appointment;
import modelsDAO.AppointmentDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AppointmentServlet", urlPatterns = "/appointments")
public class AppointmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("selectedTeacherID");
        List<Appointment> appointments = AppointmentDAO.getAppointments(Integer.parseInt(id));
        HttpSession session = req.getSession();
        session.setAttribute("appointments", appointments);
        resp.sendRedirect("./jsp/tutoria.jsp");
    }
}
