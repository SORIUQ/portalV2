package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Appointment;
import modelsDAO.UserDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CheckAppointmentInfoServlet", urlPatterns = "/checkAppInfo")
public class CheckAppointmentInfoServlet extends HttpServlet {

     @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession ses = req.getSession();
        Integer userID = Integer.parseInt(req.getParameter("studentSelected"));
        List<String> studentSeleted = UserDAO.getUserInfo(userID);
        String allInfo = studentSeleted.get(0) + " "+studentSeleted.get(1);
        ses.setAttribute("infoStudent", allInfo);
        resp.sendRedirect("./jsp/tutoriaProfesor.jsp");

    }
}
