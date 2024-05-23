<%@ page import="models.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<%
	User u = (User) session.getAttribute("user");
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
		<div class="hours">
			<h3>Horario 17:00 - 18:00</h3>
			<a class="ocupado" href="../booking?student=<%=u.getId()%>&time=17:00&day=20">Lunes 17:00 - 18:00</a>
			<a class="libre" href="../booking?student=<%=u.getId()%>&time=17:00&day=21">Martes 17:00 - 18:00</a>
			<a class="libre" href="../booking?student=<%=u.getId()%>&time=17:00&day=22">Miercoles 17:00 - 18:00</a>
			<a class="libre" href="../booking?student=<%=u.getId()%>&time=17:00&day=23">Jueves 17:00 - 18:00</a>
			<a class="libre" href="../booking?student=<%=u.getId()%>&time=17:00&day=24">Viernes 17:00 - 18:00</a>
		</div>
		<div class="hours">
			<h3>Horario 18:00 - 19:00</h3>
			<a class="libre" href="../booking?student=<%=u.getId()%>&time=18:00&day=20">Lunes 18:00 - 19:00</a>
			<a class="libre" href="../booking?student=<%=u.getId()%>&time=18:00&day=21">Martes 18:00 - 19:00</a>
			<a class="libre" href="../booking?student=<%=u.getId()%>&time=18:00&day=22">Miercoles 18:00 - 19:00</a>
			<a class="libre" href="../booking?student=<%=u.getId()%>&time=18:00&day=23">Jueves 18:00 - 19:00</a>
			<a class="libre" href="../booking?student=<%=u.getId()%>&time=18:00&day=24">Viernes 18:00 - 19:00</a>
		</div>
		<div class="hours">
			<h3>Horario 19:00 - 20:00</h3>
			<a class="ocupado" href="../booking?student=<%=u.getId()%>&time=19:00&day=20">Lunes 19:00 - 20:00</a>
			<a class="ocupado" href="../booking?student=<%=u.getId()%>&time=19:00&day=21">Martes 19:00 - 20:00</a>
			<a class="ocupado" href="../booking?student=<%=u.getId()%>&time=19:00&day=22">Miercoles 19:00 - 20:00</a>
			<a class="ocupado" href="../booking?student=<%=u.getId()%>&time=19:00&day=23">Jueves 19:00 - 20:00</a>
			<a class="libre" href="../booking?student=<%=u.getId()%>&time=19:00&day=24">Viernes 19:00 - 20:00</a>
		</div>

	</div>
	<div class="opciones">
		<a href="./tutoria.jsp" class="cancelar">Cancelar</a>
		<form action="" method="POST">
			<button class="confirmar">Confirmar Reserva</button>
		</form>
	</div>
</body>
</html>