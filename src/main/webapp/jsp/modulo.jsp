<%@ page import="models.User" %>
<%@ page import="models.Course" %>
<%@ page import="modelsDAO.CourseDAO" %>
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

<body>
<% 	if (activeUser.getUserType().equals("02")) { %>
<h3> Los profesores/as no tienen modulo asignado</h3> <% } else {%>
<%
	Course course = CourseDAO.createCourse(activeUser.getId_course()); %>
<div class="contenedorPrincipal">
	<h2><%= course.getNameCourse() %></h2>
	<div id="descriptionUnderline"> <h3> Descripción</h3> </div>
	<p><%= course.getCourseDescription() %></p>
	<table id="courseTable">
		<tr>
			<th>Asignatura</th>
			<th>Horas semanales</th>
			<th>Horas totales</th>
		</tr>
		<%
			ArrayList<Subject> subjects = mC.getSubjects(activeUser.getId_course());
			for (Subject subject : subjects) { %>
		<tr>
			<td class="tableContent"><%= subject.getSubject_name() %></td>
			<td class="tableContent" id="hoursWeekly"><%= subject.getWeekly_hours() %></td>
			<td class="tableContent" id="hoursTotal"><%= subject.getTotal_hours() %></td>
		</tr>
		<% } }%>
	</table>
</div>
</body>
</html>