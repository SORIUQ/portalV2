package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelsDAO.AppointmentDAO;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "CheckAppointmentInfoServlet", urlPatterns = "/checkAppointmentInfo")
public class CheckAppointmentInfoServlet extends HttpServlet {

     @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
         String appointment_id = req.getParameter("appointmentSelected");
         HttpSession ses = req.getSession();
         HashMap<String, Object> appointmentInfo = AppointmentDAO.getAppointmentInfo(appointment_id);

         ses.setAttribute("appointmentInfo", appointmentInfo);
         resp.sendRedirect("./jsp/tutoriaProfesor.jsp");
    }
}
