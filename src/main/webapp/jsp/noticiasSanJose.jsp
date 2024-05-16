<%@ page import="models.User" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>

<%
	User activeUser= (User) request.getSession().getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
<link href="../styles/styleNoticias.css" rel="stylesheet">
<!-- <link href="../styles/styleNoticiasSanjose.css" rel="stylesheet"> -->

<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body onload="detColoresNoticias(<%=activeUser.getId_school()%>)">
	<div class="cont">
		<h1>Semana Ignaciana y Fiestas Patronales 2024</h1>
		<h3>Semana de fiesta llena de juegos, concursos y eventos
			especiales</h3>

		<div class="imagen">
			<img alt="Juan Salvador" id="imagenSanJose" src="../images/sanJoseNoticia.jpg">
		</div>
		<div class="linea">
			<hr>
		</div>
		<div class="desarrollo">
			<p>Un año hicimos coincidir en fecha la Semana Ignaciana y las
				que son ya nuestras 49ª Fiestas Patronales, cercanos a la fiesta de
				San José. Así se está haciendo desde el COVID. En esta ocasión, a
				nivel de EDUCSI se ha vuelto a pedir la celebración de la Semana
				Ignaciana en toda España desde el 11 de marzo.</p>
			<p>Por ello, como eje central e importante estos días para
				mostrar nuestra identidad, desde Pastoral se dispondrá de una Carpa
				Ignaciana donde poder conocer la vida de San Ignacio de una manera
				lúdica. Se complementarán con actividades festivas desde el martes
				12 con el pregón de bachillerato y las prolongamos hasta el viernes
				día 15.</p>
			<p>El martes 12 a las 13:30h se realiza el pregón de
				Bachillerato. Y el miércoles a las 11h se realizará una introducción
				a las Fiestas Patronales y Semana Ignaciana por parte del profesor
				jesuita del centro Crisanto Abeso y el coordinador de Pastoral,
				Antonio J. Reyes antes de vivir el cañonazo de inicio de los días de
				fiesta.</p>
		</div>
		
	</div>
		<article>
			<div class="espacio">:D</div>
		</article>
	<script type="text/javascript" src="../scripts/script.js"></script>
</body>
</html>