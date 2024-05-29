<%@ page import="models.User" %>
<%@ page import="models.Course" %>
<%@ page import="modelsDAO.CourseDAO" %>
<%@ page import="models.Subject" %>
<%@ page import="java.util.List" %>
<%@ page import="modelsDAO.SubjectDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Modulo</title>
	<!-- FONTS -->
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
	<!-- CSS -->
	<link href="../styles/styleModulo.css" rel="stylesheet">
</head>

<%
	User activeUser = (User) request.getSession().getAttribute("user");
%>

<body onload="detColoresModulo(<%=activeUser.getSchool_id()%>)">
<% 	if (activeUser.getUserType().equals("02")) { %>
<h3> Los profesores/as no tienen módulo asignado</h3> <% } else {%>
<%
	Course course = CourseDAO.createCourse(activeUser.getCourse_id());
%>
<div class="contenedorPrincipal">
	<h2><%= course.getNameCourse() %></h2>
	<div id="descriptionUnderline" class="underline"> <h3> Descripción</h3> </div>
	<p><%= course.getCourseDescription() %></p>
	<table id="courseTable">
		<tr>
			<th>Asignatura</th>
			<th>Horas semanales</th>
			<th>Horas totales</th>
			<th>Info</th>
		</tr>
		<%
			List<Subject> subjects = SubjectDAO.getAllSubjectsByCourseId(activeUser.getCourse_id());
			for (Subject subject : subjects) { %>
		<tr>
			<td class="tableContent"><%= subject.getName() %></td>
			<td class="tableContent" id="hoursWeekly"><%= subject.getWeeklyHours() %></td>
			<td class="tableContent" id="hoursTotal"><%= subject.getTotalHours() %></td>
			<td class="tableContent" id="downloadLink">
				<a href="../descarga?subjectId=<%= subject.getSubjectId() %>">
					<img src="../images/IconoPDF.png" alt="Icono de un pdf para descargar">
				</a>
			</td>
		</tr>
		<% } }%>
	</table>
</div>

<script type="text/javascript" src="../scripts/script.js"></script>
</body>
</html>
