package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import modelsDAO.GradeDAO;

import java.io.IOException;

@WebServlet(name = "ShowGrades", urlPatterns = "/showGrades")
public class ShowGradesTeacherServlet extends HttpServlet {

    private Integer student_id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession ses = req.getSession();
        int subject_id = (Integer) ses.getAttribute("subjectSelectedID");
        if (req.getParameter("studentSelected") != null || req.getParameter("studentSelected") == null && ses.getAttribute("studentSelected") != null) {
            if (req.getParameter("studentSelected") != null && ses.getAttribute("studentSelected") != req.getParameter("studentSelected")) {
                student_id = Integer.parseInt(req.getParameter("studentSelected"));
                ses.setAttribute("studentSelected", student_id);
            }
            ses.setAttribute("gradesList", GradeDAO.getGradesFromSubjectId(subject_id, student_id));
            ses.setAttribute("teacherSelectStudentError",null);

        } else {
            ses.setAttribute("teacherSelectStudentError","Selecciona un alumno");
        }
        resp.sendRedirect("./jsp/calificaciones.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession ses = req.getSession();
        User activeUser = (User) ses.getAttribute("user");
        String description = req.getParameter("grade_description");
        Double gradeNumber = Double.valueOf(req.getParameter("gradeNumber"));
        int subject_id = (Integer) ses.getAttribute("subjectSelectedID");
        int student_id = (Integer) ses.getAttribute("studentSelected");

        GradeDAO.addGrade(ses,subject_id,activeUser.getId(),student_id,gradeNumber,description);
        doGet(req,resp);
    }
}