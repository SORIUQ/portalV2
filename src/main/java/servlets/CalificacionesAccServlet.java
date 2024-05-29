package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelsDAO.GradeDAO;

import java.io.IOException;

@WebServlet(name = "CalificacionesAccServlet", urlPatterns = "/calAcc")
public class CalificacionesAccServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // doGet implementation (if needed)
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtener el ID del estudiante del formulario
        int studentId = Integer.parseInt(req.getParameter("studentId"));
        String gradeParamName = "input_" + studentId;

        System.out.println("gradeParamName: " + gradeParamName);
        System.out.println("studentId: " + studentId);
        System.out.println("input value: " + req.getParameter(gradeParamName));

        // Verificar si el parámetro está presente en la solicitud
        if (req.getParameterMap().containsKey(gradeParamName)) {
            String grade = req.getParameter(gradeParamName);
            float studentGrade = Float.parseFloat(grade);
            System.out.println("Updating grade for student ID " + studentId + " to " + studentGrade);
            GradeDAO.updateInternshipGrade(studentId, studentGrade);
        } else {
            System.out.println("Parameter not found: " + gradeParamName);
        }

        resp.sendRedirect("./jsp/calificacionesAcc.jsp");
    }
}
