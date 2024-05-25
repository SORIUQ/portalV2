<%@ page import="models.User" %>
<%@ page import="modelsDAO.AppointmentDAO" %>
<%@ page import="models.Appointment" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<%
	User activeUser= (User) request.getSession().getAttribute("user");
	List<Appointment> appointments = AppointmentDAO.getAppointments(activeUser.getId());
	HashMap<String, String> appointmentInfo = null;
	if (session.getAttribute("appointmentInfo") != null) {
		appointmentInfo = (HashMap<String, String>) session.getAttribute("appointmentInfo");
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<link href="../styles/styleTutoria.css" rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap"
	rel="stylesheet">
<title>Reservas</title>
</head>
<body>
	<h1>RESERVAS DISPONIBLES</h1>
	<div class="reservas">
		<form action="../checkAppointmentInfo" method="get">
			<table>
				<tr>
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
					<td><input type="radio" id="<%=a.getId()%>" value="<%=a.getId()%>" name="appointmentSelected"></td>
					<%} else {%>
					<td><input type="radio" id="<%=a.getId()%>" name="appointmentSelected" disabled></td>
					<%}%>
					<%}%>
				</tr>
				<tr>
					<td>18:00 - 19:00</td>
					<%for (Appointment a : AppointmentDAO.getAppointments1800(appointments)) {%>
					<%if (a.getStudentID() != null && a.getStudentID() != 0) {%>
					<td><input type="radio" id="<%=a.getId()%>" value="<%=a.getId()%>" name="appointmentSelected" ></td>
					<%} else {%>
					<td><input type="radio" id="<%=a.getId()%>" name="appointmentSelected" disabled></td>
					<%}%>
					<%}%>
				</tr>
				<tr>
					<td>19:00 - 20:00</td>
					<%for (Appointment a : AppointmentDAO.getAppointments1900(appointments)){%>
					<%if (a.getStudentID() != null && a.getStudentID() != 0) {%>
					<td><input type="radio" id="<%=a.getId()%>" value="<%=a.getId()%>" name="appointmentSelected" ></td>
					<%} else {%>
					<td><input type="radio" id="<%=a.getId()%>" name="appointmentSelected" disabled></td>
					<%}
					}%>
				</tr>
			</table>
			<input type="submit" >
		</form>
	</div>
	<div class = "infoConsulta">
		<% if (appointmentInfo == null ) {%>
			<p>Seleccione una cita para ver la información</p>
		<%} else {%>
			<p>Estudiante: <%=appointmentInfo.get("studentName") + " " + appointmentInfo.get("studentSurname")%></p>
			<p>Hora: <%=appointmentInfo.get("time")%></p>
			<p>Día: <%=appointmentInfo.get("date")%></p>
			<!-- <p>Despacho: appointmentInfo.get("room")</p> -->
		<%}%>
	</div>
</body>
</html>