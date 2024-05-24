<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ page import="java.sql.*,utils.*,models.*,java.util.List"%>
<%@ page import="modelsDAO.UserDAO"%>
<%@ page import="modelsDAO.AppointmentDAO" %>

<%
	User activeUser= (User) request.getSession().getAttribute("user");
	List<User> teachers = UserDAO.getAllNameTeachers(activeUser.getId_course(), activeUser.getId_school());
	List<Appointment> appointments = (List<Appointment>) session.getAttribute("appointments");
	String apmOk = (String) session.getAttribute("apmOk");
	String apmError = (String) session.getAttribute("apmError");
	String deleteMSG = (String) session.getAttribute("deleteMSG");
%>

<!DOCTYPE html>
<html>
<head>
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<% if (activeUser.getUserType().equals("02")) { %>
	<link href="../styles/styleTutoria.css" rel="stylesheet">
	<% } %>
	<% if (activeUser.getUserType().equals("01")) { %>
	<link href="../styles/styleReserva.css" rel="stylesheet">
	<% } %>
	<link
			href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap"
			rel="stylesheet">
	<meta charset="UTF-8">
	<title>Tutorías</title>
</head>
<% if (activeUser.getUserType().equals("01")) { %>
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

<% if (apmOk != null) {%>
	<p class="okMsg"><%=apmOk%></p>
<%}%>

<% if (apmError != null) {%>
	<p class="errorMsg"><%=apmError%></p>
<%}%>

<% if (deleteMSG != null) {%>
<p class="errorMsg"><%=deleteMSG%></p>
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
<% } %>



<% if (activeUser.getUserType().equals("02")) { %>
<body>
<h1>TUTORÍAS</h1>
<div id="contenido">
	<div class="informacion">
		<h2>Asignatura</h2>
		<h2>Lugar de las tutorías</h2>
		<p>
			<b>Horario:</b> de lunes a viernes de 17:00 a 20:00
		</p>
	</div>

	<div class="tablaReservas">
		<table>
			<tr>
				<th></th>
				<th>L-20</th>
				<th>M-21</th>
				<th>X-22</th>
				<th>J-23</th>
				<th>V-24</th>
			</tr>
			<tr>
				<td>17:00 - 18:00</td>
				<td class="libre" data-id="Lunes 17:00-18:00"></td>
				<td class="libre" data-id="Martes 17:00-18:00"></td>
				<td class="libre" data-id="Miércoles 17:00-18:00"></td>
				<td class="libre" data-id="Jueves 17:00-18:00"></td>
				<td class="libre" data-id="Viernes 17:00-18:00"></td>
			</tr>
			<tr>
				<td>18:00 - 19:00</td>
				<td class="libre" data-id="Lunes 18:00-19:00"></td>
				<td class="libre" data-id="Martes 18:00-19:00"></td>
				<td class="libre" data-id="Miércoles 18:00-19:00"></td>
				<td class="libre" data-id="Jueves 18:00-19:00"></td>
				<td class="libre" data-id="Viernes 18:00-19:00"></td>
			</tr>
			<tr>
				<td>19:00 - 20:00</td>
				<td class="libre" data-id="Lunes 19:00-20:00"></td>
				<td class="libre" data-id="Martes 19:00-20:00"></td>
				<td class="libre" data-id="Miércoles 19:00-20:00"></td>
				<td class="libre" data-id="Jueves 19:00-20:00"></td>
				<td class="libre" data-id="Viernes 19:00-20:00"></td>
			</tr>
		</table>

		<div class="legend">
			<div>
				<span class="ocupado"></span> Ocupados
			</div>
			<div>
				<span class="libre"></span> Libres
			</div>
		</div>
	</div>
</div>

<script src="../scripts/tutorias.js"></script>
</body>
<% } %>
</html>