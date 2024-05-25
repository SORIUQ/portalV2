<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="utils.*,models.*"%>
<%@ page import="modelsDAO.SchoolDAO" %>

<%
User activeUser = (User) session.getAttribute("user");
School school = SchoolDAO.createSchool(activeUser.getSchool_id());
String imagen = "." + Util.defineImageIndex(school.getIdSchool());
%>

<!DOCTYPE html>
<html>
<head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="../styles/styleCentro.css" rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap"
	rel="stylesheet">
<link rel="icon" type="image/x-icon" href="./images/accFavicon.jpg">
<meta charset="UTF-8	">
<title>Portal - Inicio</title>
</head>
<body onload="detColoresCentro(<%=activeUser.getSchool_id()%>)">

	<div class="contenedorPrincipal">
		<div class="headerContenedor">
			<h2 id="schoolTitle"><%=school.getSchoolName()%></h2>
			<img src="<%=imagen%>" alt="imagen del centro"/>
		</div>
			<h4>Informacion de contacto</h4>
			<table>
				<tr>
					<th>Número de teléfono:</th>
					<th class="tableContent"><%=school.getTlfSchool()%></th>
				</tr>
				<tr>
					<th>Email :</th>
					<th class="tableContent"><%=school.getEmail()%></th>
				</tr>
				<tr>
					<th>Horarios de Secretaría :</th>
					<th class="tableContent"><%=school.getScheduleSchool()%></th>
				</tr>
				<tr>
					<th>Municipio :</th>
					<th class="tableContent"><%=school.getLocSchool()%></th>
				</tr>
			</table>
			<h4>¿Cómo llegar?</h4>
			<iframe src=<%=school.getMapLink()%> class="mapaEscuela" width="1000" height="500"
				style="border: 0;" allowfullscreen="" loading="lazy"
				referrerpolicy="no-referrer-when-downgrade"></iframe>
		</div>

	<script type="text/javascript" src="../scripts/script.js"></script>
</body>
</html>