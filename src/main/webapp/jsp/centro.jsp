<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="java.sql.*,utils.*,models.*"%>
<%

User activeUser = (User) session.getAttribute("user");
School sch = Util.getInfoSchool(activeUser.getId_school());
String imagen = Util.defineImage(sch.getIdSchool());
String mapLink = Util.defineMap(sch.getIdSchool());

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
<body>

	<div class="contenedorPrincipal">
		<div class="headerContenedor">
			<h2 id="schoolTitle"><%=sch.getNombreSchool()%></h2>
			<img src=<%=imagen%>>
		</div>
			<h4>Informacion de contacto</h4>
			<table>
				<tr>
					<th>Número de teléfono:</th>
					<th class="tableContent"><%=sch.getTlfSchool()%></th>
				</tr>
				<tr>
					<th>Email :</th>
					<th class="tableContent"><%=sch.getEmail()%></th>
				</tr>
				<tr>
					<th>Horarios de Secretaría :</th>
					<th class="tableContent"><%=sch.getScheduleSchool()%></th>
				</tr>
				<tr>
					<th>Municipio :</th>
					<th class="tableContent"><%=sch.getLocSchool()%></th>
				</tr>
			</table>
			<h4>¿Cómo llegar?</h4>
			<iframe src=<%=mapLink%> class="mapaEscuela" width="1000" height="500"
				style="border: 0;" allowfullscreen="" loading="lazy"
				referrerpolicy="no-referrer-when-downgrade"></iframe>
		</div>
</body>
</html>