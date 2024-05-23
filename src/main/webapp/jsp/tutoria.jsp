<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ page import="java.sql.*,utils.*,models.*,java.util.List"%>
<%@ page import="modelsDAO.UserDAO"%>
<%@ page import="modelsDAO.AppointmentDAO" %>
<%@ page import="java.util.ArrayList" %>

<%
	User activeUser= (User) request.getSession().getAttribute("user");
	List<User> teachers = UserDAO.getAllNameTeachers(activeUser.getId_course(), activeUser.getId_school());
	List<Appointment> appointments = (List<Appointment>) session.getAttribute("appointments");
	String selectedTeacher = (String) session.getAttribute("selectedTeacherID");
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
<div class="reservas">
	<h3><%=selectedTeacher%></h3>
	<table>
		<%if (appointments != null) {
			for (Appointment a : appointments) {%>
			<p><%=a.getId()%></p>
			<%}
		}%>
	</table>
</div>
<div class="opciones">
	<a href="./tutoria.jsp" class="cancelar">Cancelar</a>
	<form action="" method="POST">
		<button class="confirmar">Confirmar Reserva</button>
	</form>
</div>
<script src="../scripts/tutorias.js"></script>
</body>
<% } %>



<% if (activeUser.getUserType().equals("02")) { %>
<body>
<h1>TUTORÍAS</h1>
<div class="contenido">
	<div class="informacion">
		<h2>Asignatura</h2>
		<h2>Lugar de las tutorías</h2>
		<p>
			<strong>Horario:</strong> de lunes a viernes de 17:00 a 20:00
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