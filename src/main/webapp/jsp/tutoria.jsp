<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*,utils.*,models.*,java.util.List"%>
<%@ page import="modelsDAO.UserDAO"%>
<!DOCTYPE html>
<html>
<head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="../styles/styleTutoria.css" rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap"
	rel="stylesheet">
<meta charset="UTF-8">
<title>Tutorías</title>
<%

User activeUser= (User) request.getSession().getAttribute("user");

List<String> profesores = UserDAO.getAllNameTeachers();
%>
</head>
<body>
	<h1>TUTORÍAS</h1>

	<select id="profesorSelect">
		<option disabled selected>Seleccione profesor/a</option>
		<%
		for (int i = 0; i < profesores.size(); i++) {
		%>
		<option><%=profesores.get(i)%></option>
		<%
		}
		%>
	</select>
	<div id="contenido" style="display: none">
		<div class="informacion">
			<h2>Asignatura</h2>
			<h2>Lugar de las tutorías</h2>
			<p><strong>Horario:</strong> de lunes a viernes de 17:00 a 20:00</p>
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
					<td class="libre"></td>
					<td class="libre"></td>
					<td class="libre"></td>
					<td class="libre"></td>
					<td class="libre"></td>
				</tr>
				<tr>
					<td>18:00 - 19:00</td>
					<td class="libre"></td>
					<td class="libre"></td>
					<td class="libre"></td>
					<td class="libre"></td>
					<td class="libre"></td>
				</tr>
				<tr>
					<td>19:00 - 20:00</td>
					<td class="libre"></td>
					<td class="libre"></td>
					<td class="libre"></td>
					<td class="libre"></td>
					<td class="libre"></td>
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
		<% if (activeUser.getUserType().equals("01")) { %>
		<div class="reservar">
			<a href="./reserva.jsp">Reservar</a>
		</div>
		<% } %>
	</div>

	<script src="../scripts/tutorias.js"></script>

</body>
</html>