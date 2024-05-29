package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelsDAO.GradeDAO;

import java.io.IOException;

@WebServlet(name = "DeleteGradeServlet", urlPatterns = "/deleteGrade")
public class DeleteGradeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession ses = req.getSession();
        int subjectID = (Integer)ses.getAttribute("subjectSelectedID");
        int studentID = (Integer)ses.getAttribute("studentSelected");

        ses.setAttribute("gradesList", GradeDAO.getGradesFromSubjectId(subjectID,studentID));

        resp.sendRedirect("./jsp/calificaciones.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int teacher_id = Integer.parseInt(req.getParameter("gradeTeacherId"));
        int student_id = Integer.parseInt(req.getParameter("gradeStudentId"));
        int subject_id = Integer.parseInt(req.getParameter("gradeSubjectId"));
        String description = req.getParameter("gradeDescription");
        double grade = Double.parseDouble(req.getParameter("grade"));

        req.getSession().setAttribute("subjectSelectedID", subject_id);
        GradeDAO.deleteGrade(teacher_id, student_id, subject_id, description, grade);
        doGet(req,resp);
    }
}
