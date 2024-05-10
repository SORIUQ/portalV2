<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="java.sql.*,utils.*,models.*"%>
<%

	User activeUser = (User) session.getAttribute("user");
	School sch=Util.getInfoSchool(activeUser.getId_school());
	String imagen=Util.defineImage(sch.getIdSchool());
	
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
		
		<h2 id="schoolTitle"><%=sch.getNombreSchool()%></h2>
		<h3>Informacion de contacto</h3>
		<table>
			<tr>
				<th>Numero de telefono:</th>
				<th><%=sch.getTlfSchool() %></th>
			</tr>
			<tr>
				<th>Email :</th>
				<th><%=sch.getEmail() %></th>
			</tr>
			<tr>
				<th>Horarios de Secretaria :</th>
				<th><%=sch.getScheduleSchool() %></th>
			</tr>
			<tr>
				<th>Municipio :</th>
				<th><%=sch.getLocSchool() %></th>
			</tr>
		</table>
	</div>
</body>
</html>