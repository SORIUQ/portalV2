<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, utils.*, models.*"%>
<%@page import="java.util.List"%>
<%@ page import="modelsDAO.UserDAO" %>

<%
	User activeUser= (User) request.getSession().getAttribute("user");
	int id = activeUser.getId();
	List<String> user = UserDAO.getUserInfo(id);
	String errorMsg = (String) session.getAttribute("errorMsg");
	String okMsg = (String) session.getAttribute("okMsg");
%>


<!DOCTYPE html>
<html>
<head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="../styles/styleDatosPersonales.css" rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap"
	rel="stylesheet">
<meta charset="UTF-8">
<title>Datos Personales</title>
</head>

<body onload="detColoresdp(<%=activeUser.getId_school()%>)">
	<h1 class="h1dp">Datos Personales</h1>

	<div class="tarjetaPersonal">

		<div class="ladoIzquierdoTarjeta">
			<div class="columna1">
				<div>
					<h3>Nombre</h3>
					<p><%=user.get(0)%></p>
				</div>
				<div>
					<h3>Dni</h3>
					<p><%=user.get(2)%></p>
				</div>
			</div>
			<div class="columna2">
				<div>
					<h3>Apellidos</h3>
					<p><%=user.get(1)%></p>
				</div>
				<div>
					<h3>Fecha de Nacimiento</h3>
					<p><%=Util.dateFormat(user.get(3))%></p>
				</div>
			</div>
		</div>

		<div class="ladoDerechoTarjeta">
			<svg viewBox="0 0 24 24" fill="none"
				xmlns="http://www.w3.org/2000/svg" stroke="#ffffff">
				<g id="SVGRepo_bgCarrier" stroke-width="0"></g>
				<g id="SVGRepo_tracerCarrier" stroke-linecap="round"
					stroke-linejoin="round"></g>
				<g id="SVGRepo_iconCarrier"> <path
					d="M5 21C5 17.134 8.13401 14 12 14C15.866 14 19 17.134 19 21M16 7C16 9.20914 14.2091 11 12 11C9.79086 11 8 9.20914 8 7C8 4.79086 9.79086 3 12 3C14.2091 3 16 4.79086 16 7Z"
					stroke="#000000" stroke-width="2" stroke-linecap="round"
					stroke-linejoin="round"></path> </g></svg>
		</div>



	</div>
	
	<div class="buttonDiv">
	<button id="passChangeButton" onclick="showChangeInput()"
	onsubmit="comprobarPass">Cambiar Contraseña
	</button>
	<% if (errorMsg!=null){ %>
		<p class="errorMsg"><%=errorMsg %></p>
	<% } %>

	<% if (okMsg!=null){ %>
		<p class="okMsg"><%=okMsg %></p>
	<% } %>
	</div>
	<div id="inputPass" class="hiddenPass">
	<form action="../passChange" method="POST">
	    <p> Antigua contraseña </p>
		<input type="password" id="oldPass" name="user_oldPassword"></input>
		<p> Nueva contraseña </p>
		<input type="password" id="newPass" name="user_newPassword"></input>
		
		<div class="passNotValidShow" id="errorPass">
			<p>¡Password demasiado débil! La contraseña debe tener:
				<ul>
				<li>Una mayúscula</li>
				<li>Una minuscula</li>
				<li>8 carácteres</li>
				<li>Un número</li>
				</ul>
			</p>
		</div>
		<br>
		<button id="botonInput"type="submit" disabled="true">Hecho</button>
	</form>
	</div>

	<script src="../scripts/passChange.js"></script>
	
</body>
</html>