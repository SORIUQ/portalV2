<%@ page import="models.User" %>
<%@ page import="modelsDAO.AppointmentDAO" %>
<%@ page import="models.Appointment" %>
<%@ page import="modelsDAO.UserDAO" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<%
	User activeUser= (User) request.getSession().getAttribute("user");
	List<Appointment> appointments = (List<Appointment>) session.getAttribute("appointments");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="../styles/styleReserva.css" rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap"
	rel="stylesheet">
<title>Reservas</title>
</head>
<body>
	<h1>RESERVAS DISPONIBLES</h1>
	<div class="reservas">
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
	</div>
</body>
</html>