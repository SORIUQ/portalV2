package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import modelsDAO.GradeDAO;
import modelsDAO.UserDAO;

import java.util.*;
import java.io.IOException;

@WebServlet(name ="TeacehrGradesServlet", urlPatterns = "/teacherGrades")
public class TeacherGradesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession ses = req.getSession();
        User activeUser = (User) ses.getAttribute("user");
        Integer subject_id = Integer.valueOf(req.getParameter("subjectSelected"));
        ses.setAttribute("subjectSelectedID", subject_id);
        ses.setAttribute("subjectSelected", GradeDAO.getSubjectNameFromId(subject_id));
        List<User> students = UserDAO.getStudentsFromTeacherAndSubjectId(activeUser,subject_id);


        ses.setAttribute("subjectStudents", students);
        resp.sendRedirect("./jsp/calificaciones.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
