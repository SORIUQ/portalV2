<%@ page import="models.User" %>
<%@ page import="java.util.List" %>
<%@ page import="modelsDAO.UserDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<%
	User user = (User) request.getSession().getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="../styles/styleCalificaciones.css" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
	<meta charset="UTF-8">
	<title></title>
</head>
<body>
<%
	if (user.getUserType().equals("02")){
		List<User> alumnos = UserDAO.getStudentsFromTeacher(user.getId());
%>
		<select name="alumnosSelect">
			<option value="0" disabled selected>-- Seleccione un centro --</option>
			<%for(int i = 0; i < alumnos.size(); i++) { %>
				<option value="<%= alumnos.get(i)%>"><%= alumnos.get(i).getName()%></option>
			<%}%>
		</select>
	<%}%>
<h4>Calificaciones del centro escolar</h4>

<h4>Calificaciones de la formación dual</h4>
</body>
</html>