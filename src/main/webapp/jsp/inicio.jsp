<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="models.*" %>
   
<% User activeUser= (User) request.getSession().getAttribute("user"); %>
<!DOCTYPE html>
<html>
<head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="../styles/styleInicio.css" rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap"
	rel="stylesheet">
<meta charset="UTF-8">
<title>Inicio</title>
</head>
<body onload="detColorInicio(<%=activeUser.getSchool_id()%>)">
<div class="container">
	<h2>¡Bienvenido/a <%=activeUser.getName() %>!</h2>
	<div class="divider"></div>
	</div>
</body>
<script type="text/javascript" src="../scripts/script.js"></script>
</html>