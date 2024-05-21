<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<p class="ocupado">Lunes 17:00 - 18:00</p>
			<p class="libre">Martes 17:00 - 18:00</p>
			<p class="libre">Miercoles 17:00 - 18:00</p>
			<p class="libre">Jueves 17:00 - 18:00</p>
			<p class="libre">Viernes 17:00 - 18:00</p>
		</div>
		<div class="hours">
			<h3>Horario 18:00 - 19:00</h3>
			<p class="libre">Lunes 18:00 - 19:00</p>
			<p class="libre">Martes 18:00 - 19:00</p>
			<p class="libre">Miercoles 18:00 - 19:00</p>
			<p class="libre">Jueves 18:00 - 19:00</p>
			<p class="libre">Viernes 18:00 - 19:00</p>
		</div>
		<div class="hours">
			<h3>Horario 19:00 - 20:00</h3>
			<p class="ocupado">Lunes 19:00 - 20:00</p>
			<p class="ocupado">Martes 19:00 - 20:00</p>
			<p class="ocupado">Miercoles 19:00 - 20:00</p>
			<p class="ocupado">Jueves 19:00 - 20:00</p>
			<p class="ocupado">Viernes 19:00 - 20:00</p>
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