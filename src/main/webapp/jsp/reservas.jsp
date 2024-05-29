
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.*,java.util.List"%>
<%@ page import="modelsDAO.UserDAO"%>
<%@ page import="modelsDAO.AppointmentDAO"%>

<%
	User activeUser = (User) request.getSession().getAttribute("user");
	if (activeUser.getUserType().equals("02")) {response.sendRedirect("./tutoriaProfesor.jsp");}
	List<User> teachers = UserDAO.getAllNameTeachers(activeUser.getCourse_id(), activeUser.getSchool_id());
	List<Appointment> appointments = (List<Appointment>) session.getAttribute("appointments");
	String okMsg = (String) session.getAttribute("appointmentOk");
	String errorMsg = (String) session.getAttribute("appointmentError");
	String subject = "";
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
<body onload="detColoresTutorias(<%=activeUser.getSchool_id()%>)">
<button id="idUsuario" style="display:none;" value="<%= activeUser.getId() %>"></button>
<h4>RESERVAS DISPONIBLES</h4>
<form action="../appointments" method="get" >
	<select id="profesorSelect" name="selectedTeacherID" class="profesorSelect">
		<option disabled selected>Seleccione profesor/a</option>
		<%for (User teacher : teachers) {%>
			<option value="<%=teacher.getId()%>"><%=teacher.getName()%></option>
		<%}%>
	</select>
	<input id="buttonSubmit" type="submit" class="buttonSubmit">
</form>
	<% if (okMsg != null) {%>
		<p class="okMsg"><%=okMsg%></p>
		<%session.setAttribute("appointmentOk", null);
	}%>

	<% if (errorMsg != null) {%>
		<p class="errorMsg"><%=errorMsg%></p>
		<%session.setAttribute("appointmentError", null);
	}%>

	<div id="reservas">
		<% if (appointments != null) {%>

		<div class="informacion">
			<div class="informacionConcreta">
				<h3>Asignatura: <span class="Letras"><%= UserDAO.getSubjectsTeacher(Integer.parseInt((String) session.getAttribute("selectedTeacherID"))) %></span></h3>
				<h3>Lugar: <span class="Letras"><%= UserDAO.getNameSchoolTeacher(Integer.parseInt((String) session.getAttribute("selectedTeacherID"))) %></span> </h3>
				<p><strong>Horario:</strong> de lunes a viernes de 17:00 a 20:00</p>
			</div>
			<div class="legend">
				<div>
					<span class="ocupado"></span> Ocupados
				</div>
				<div>
					<span class="libre"></span> Libres
				</div>
				<div>
					<span class="tuReserva"></span> Tu Reserva
				</div>
			</div>
		</div>
		<form action="../appointments" method="post">
			<table class="tablaReservas">
				<tr>
					<th></th>
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
					<td>
					    <input type="radio" id="<%=a.getId()%>" value="<%=a.getId()%>" name="hourSelectedID" disabled data-student-id="<%=a.getStudentID()%>">
					</td>
					<%} else {%>
					<td>
					    <input type="radio" id="<%=a.getId()%>" value="<%=a.getId()%>" name="hourSelectedID" data-student-id="0">
					</td>
					<%}%>
					<%}%>
				</tr>
				<tr>
					<td>18:00 - 19:00</td>
					<%for (Appointment a : AppointmentDAO.getAppointments1800(appointments)) {%>
					<%if (a.getStudentID() != null && a.getStudentID() != 0) {%>
					<td>
					    <input type="radio" id="<%=a.getId()%>" value="<%=a.getId()%>" name="hourSelectedID" disabled data-student-id="<%=a.getStudentID()%>">
					</td>
					<%} else {%>
					<td>
					    <input type="radio" id="<%=a.getId()%>" value="<%=a.getId()%>" name="hourSelectedID" data-student-id="0">
					</td>
					<%}%>
					<%}%>
				</tr>
				<tr>
					<td>19:00 - 20:00</td>
					<%for (Appointment a : AppointmentDAO.getAppointments1900(appointments)) {%>
					<%if (a.getStudentID() != null && a.getStudentID() != 0) {%>
					<td>
					    <input type="radio" id="<%=a.getId()%>" value="<%=a.getId()%>" name="hourSelectedID" disabled data-student-id="<%=a.getStudentID()%>">
					</td>
					<%} else {%>
					<td>
					    <input type="radio" id="<%=a.getId()%>" value="<%=a.getId()%>" name="hourSelectedID" data-student-id="0">
					</td>
					<%}%>
					<%}%>
				</tr>
			</table>
			<div class="opcionReservar">
				<input type="submit" class="botonReserva" value="Hacer Reserva">
			</div>
		</form>
		<form action="../deleteAppointment" method="post" class="opcionCancelar">
			<button class="botonCancelar" type="submit">Cancelar Reserva</button>
		</form>

		<%}%>
	</div>

	<script type="text/javascript">
	const radios = document.querySelectorAll('input[type="radio"]');
	const idUsuario = document.getElementById('idUsuario').value;

	radios.forEach(radio => {
	    const studentId = radio.getAttribute('data-student-id');

	    if (radio.disabled) {
	        radio.closest('td').classList.add('ocupado');
	    }

	    if(studentId == idUsuario) {
	        radio.closest('td').classList.add('tuReserva');
	    }
	});
	</script>
	<script type="text/javascript" src="../scripts/script.js"></script>
</body>
</html>