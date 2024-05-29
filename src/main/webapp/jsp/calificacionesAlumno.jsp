<%@ page import="models.User"%>
<%@ page import="modelsDAO.UserDAO"%>
<%@ page import="models.Course"%>
<%@ page import="modelsDAO.CourseDAO"%>
<%@ page import="models.Subject"%>
<%@ page import="modelsDAO.SubjectDAO"%>
<%@ page import="java.util.List"%>
<%@ page import="models.Grade" %>
<%@ page import="modelsDAO.GradeDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    User user = (User) request.getSession().getAttribute("user");
    List<Subject> asignaturasAlumno = SubjectDAO.getAllSubjectsByUser(user);
    String nombreSelected = (String) session.getAttribute("nombreSelected");
    List <Grade> allGrades = (List<Grade>) session.getAttribute("grades");
    String errorMsgCaliUser = (String)session.getAttribute("errorMsgCaliUser");
   
%>

<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="../styles/styleCalificaciones.css" rel="stylesheet">
    <link
            href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap"
            rel="stylesheet">
    <meta charset="UTF-8">
    <title>Calificaciones Alumno</title>
</head>
<body onload="detColoresCalificaciones(<%=user.getSchool_id()%>)">
<h4>Calificaciones de <%=user.getName()%></h4>

<div class="divCali">
<form action="../studentGrades" method="GET">
    <select class="asignaturas" name="subjectSelected" id="select">
        <option value="0" disabled selected>-- Seleccione una asignatura --</option>
        <%for (Subject subject : asignaturasAlumno) {%>
            <option value="<%=subject.getSubjectId()%>" name="subjectSelected"><%=subject.getName()%></option>
        <%}%>
        <input type="submit" value="Comprobar">
    </select>
    <%if (errorMsgCaliUser!=null){%>
        <p class="errorMsg"><%=errorMsgCaliUser %></p>
        <%session.setAttribute("errorMsgCaliUser",null);
    }%>
</form>

</div>
    <%if (nombreSelected != null) {%>
        <h4><%=nombreSelected%></h4>
    <%}%>
    <%if (allGrades == null) {%>
        <p></p>
    <%} else if (allGrades.isEmpty()) {%>
        <h2>No hay notas para esta asignatura</h2>
    <%} else {
        for (Grade g : allGrades) {
            if (g.getGrade() != null) {%>
                <p><%=g.getGrade_desc()%> --- <%=g.getGrade()%></p>
            <%} else {%>
                <p><%=g.getGrade_desc()%> --- Nota no disponible</p>
            <%}
        }
    }%>

<script type="text/javascript" src="../scripts/script.js"></script>
</body>
</html>
