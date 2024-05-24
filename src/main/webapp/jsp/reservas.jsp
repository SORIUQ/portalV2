<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ page import="java.sql.*,utils.*,models.*,java.util.List"%>
<%@ page import="modelsDAO.UserDAO"%>
<%@ page import="modelsDAO.AppointmentDAO" %>

<%
	User activeUser= (User) request.getSession().getAttribute("user");
	if (activeUser.getUserType().equals("02")) {response.sendRedirect("./tutoriaProfesor.jsp");}
	List<User> teachers = UserDAO.getAllNameTeachers(activeUser.getId_course(), activeUser.getId_school());
	List<Appointment> appointments = (List<Appointment>) session.getAttribute("appointments");
	String okMsg = (String) session.getAttribute("okMsg");
	String errorMsg = (String) session.getAttribute("errorMsg");
%>

<!DOCTYPE html>
<html>
<head>
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<% if (activeUser.getUserType().equals("01")) { %>
	<link href="../styles/styleReserva.css" rel="stylesheet">
	<% } %>
	<link
			href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap"
			rel="stylesheet">
	<meta charset="UTF-8">
	<title>Tutorías</title>
</head>
<body>
<h1>RESERVAS DISPONIBLES</h1>
<form action="../appointments" method="get">
	<select id="profesorSelect" name="selectedTeacherID">
		<option disabled selected>Seleccione profesor/a</option>
		<%for (User teacher : teachers) {%>
			<option value="<%=teacher.getId()%>"><%=teacher.getName()%></option>
		<%}%>
	</select>
	<input id="buttonSubmit" type="submit">
</form>

<% if (okMsg != null) {%>
	<p class="okMsg"><%=okMsg%></p>
<%}%>

<% if (errorMsg != null) {%>
	<p class="errorMsg"><%=errorMsg%></p>
<%}%>

<div id="reservas">
	<%if (appointments == null) {%>
		<p>Seleccione un profesor</p>
	<%} else {%>
		<form action="../appointments" method="post">
			<table>
				<tr>
					<th> </th>
					<th>Lunes</th>
					<th>Martes</th>
					<th>Miércoles</th>
					<th>Jueves</th>
					<th>Viernes</th>
				</tr>
				<tr>
					<td>17:00 - 18:00</td>
					<%for (Appointment a : AppointmentDAO.getAppointments1700(appointments)) {%>
						<%if (a.getStudentID() != null && a.getStudentID() != 0) {%>
							<td><input type="radio" id="<%=a.getId()%>" value="<%=a.getId()%>" name="hourSelectedID" disabled></td>
						<%} else {%>
							<td><input type="radio" id="<%=a.getId()%>" value="<%=a.getId()%>" name="hourSelectedID"></td>
						<%}%>
					<%}%>
				</tr>
				<tr>
					<td>18:00 - 19:00</td>
					<%for (Appointment a : AppointmentDAO.getAppointments1800(appointments)) {%>
						<%if (a.getStudentID() != null && a.getStudentID() != 0) {%>
							<td><input type="radio" id="<%=a.getId()%>" value="<%=a.getId()%>" name="hourSelectedID" disabled></td>
						<%} else {%>
							<td><input type="radio" id="<%=a.getId()%>" value="<%=a.getId()%>" name="hourSelectedID"></td>
						<%}%>
					<%}%>
				</tr>
				<tr>
					<td>19:00 - 20:00</td>
					<%for (Appointment a : AppointmentDAO.getAppointments1900(appointments)){%>
						<%if (a.getStudentID() != null && a.getStudentID() != 0) {%>
							<td><input type="radio" id="<%=a.getId()%>" value="<%=a.getId()%>" name="hourSelectedID" disabled></td>
						<%} else {%>
							<td><input type="radio" id="<%=a.getId()%>" value="<%=a.getId()%>" name="hourSelectedID"></td>
						<%}
					}%>
				</tr>
			</table>
			<div id="opciones"></div>
			<input type="submit" class="botonReserva" value="Hacer Reserva">
		</form>
		<form action="../deleteAppointment" method="post">
			<button type="submit">Cancelar Reserva</button>
		</form>

	<%}%>
</div>
</body>
</html>