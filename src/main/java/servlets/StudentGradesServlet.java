package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Grade;
import models.Subject;
import models.User;
import modelsDAO.GradeDAO;
import modelsDAO.SubjectDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "StudentGradesServlet", urlPatterns = "/studentGrades")
public class StudentGradesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession ses = req.getSession();
        User activeUser = (User) ses.getAttribute("user");
        if (req.getParameter("subjectSelected")!= null) {
        Integer subject_id = Integer.parseInt(req.getParameter("subjectSelected"));
        if (subject_id != null) {
            ses.setAttribute("nombreSelected",GradeDAO.getSubjectNameFromId(subject_id));
        }
        List<Subject> subjects = SubjectDAO.getSubjectsFromStudentGrades(activeUser);
        ses.setAttribute("subjects",subjects);
        List<Grade> grades = null;
        if (Integer.parseInt(req.getParameter("subjectSelected")) != 12) {
            grades = GradeDAO.getGradesFromSubjectId(subject_id, activeUser);
        } else {
            grades = GradeDAO.getInternshipGradeOfStudent(activeUser.getId());
        }
        ses.setAttribute("grades",grades);
    	ses.setAttribute("errorMsgCaliUser", null);
        resp.sendRedirect("./jsp/calificacionesAlumno.jsp");
        } else {
        	ses.setAttribute("errorMsgCaliUser", "Seleccione una asignatura");
        	resp.sendRedirect("./jsp/calificacionesAlumno.jsp");
        }
    }

}

