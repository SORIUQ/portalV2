<%@page import="java.util.concurrent.ScheduledFuture"%>
<%@ page import="models.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<%@ page import="java.sql.*,utils.*,models.*" %>
<%@ page import="modelsDAO.CourseDAO" %>
<%@ page import="modelsDAO.SchoolDAO" %>
<%@ page import="modelsDAO.UserDAO" %>
<%
	User activeUser= (User) request.getSession().getAttribute("user");
	Integer idSchool = null;
	String imagen="";

	School sch=null;
	String contentTarjeta="";
	String courseName="";
	Course crs=null;
	String centroUsuario="";
	String pathCalificaciones = "";

	if ( session.getAttribute("user") == null ){
		response.sendRedirect("./jsp/login.jsp");
		return;
	} else {
		idSchool = activeUser.getSchool_id();
		imagen = Util.defineImageIndex(idSchool);
		sch = SchoolDAO.createSchool(idSchool);
		if (activeUser.getCourse_id() != null)
			crs = CourseDAO.createCourse(activeUser.getCourse_id());
		if (sch.getSchoolName() != null)
			centroUsuario = sch.getSchoolName();
		else
			centroUsuario = "Accenture";
		contentTarjeta = Util.getContentTarjetaIndex(activeUser,centroUsuario,crs);
		pathCalificaciones = UserDAO.calificacionesByUserType(activeUser);
	}
%>
<!DOCTYPE html>
<html>

<head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="./styles/style.css" rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap"
	rel="stylesheet">
<link rel="icon" type="image/x-icon" href="./images/accFavicon.jpg">
<meta charset="UTF-8">
<title>Portal - Inicio</title>
</head>

<body onload="determinarColores(<%=activeUser.getSchool_id()%>)">
	<header>
		<div class="imagenNombreCentro">
			<img src=<%=imagen %> alt="imagenCentro"
				style="height: 10vh">
			<h1><%=centroUsuario%></h1>
		</div>
		<div class="tarjetaAlumno">
			<div class="nombreCurso">
				<p><%=activeUser.getName()%></p>
				<p><%=contentTarjeta%></p>
			</div>
			<img src="./images/imageUser.png" alt="imagenAlumno">
		</div>
	</header>

	<div class="contenedor">
		<article class="menu">
			<div class="menuOpciones">
				<%
					if(activeUser != null && (activeUser.getUserType().equals("01") || activeUser.getUserType().equals("02"))) {
				%>
				<div class="menuOpcion" id="0" onclick="cambiarContenido('./jsp/noticias.jsp', id)">
       <svg xmlns="http://www.w3.org/2000/svg" width="125" height="125"
          viewBox="0 0 24 24">
          <path fill="#a100ff"
             d="M18.75 20H5.25a3.25 3.25 0 0 1-3.245-3.066L2 16.75V6.25a2.25 2.25 0 0 1 2.096-2.245L4.25 4h12.5a2.25 2.25 0 0 1 2.245 2.096L19 6.25V7h.75a2.25 2.25 0 0 1 2.245 2.096L22 9.25v7.5a3.25 3.25 0 0 1-3.066 3.245zH5.25zm-13.5-1.5h13.5a1.75 1.75 0 0 0 1.744-1.607l.006-.143v-7.5a.75.75 0 0 0-.648-.743L19.75 8.5H19v7.75a.75.75 0 0 1-.648.743L18.25 17a.75.75 0 0 1-.743-.648l-.007-.102v-10a.75.75 0 0 0-.648-.743L16.75 5.5H4.25a.75.75 0 0 0-.743.648L3.5 6.25v10.5a1.75 1.75 0 0 0 1.606 1.744zh13.5zm6.996-4h3.006a.75.75 0 0 1 .102 1.493l-.102.007h-3.006a.75.75 0 0 1-.102-1.493zh3.006zm-3.003-3.495a.75.75 0 0 1 .75.75v3.495a.75.75 0 0 1-.75.75H5.748a.75.75 0 0 1-.75-.75v-3.495a.75.75 0 0 1 .75-.75zm-.75 1.5H6.498V14.5h1.995zm3.753-1.5h3.006a.75.75 0 0 1 .102 1.493l-.102.007h-3.006a.75.75 0 0 1-.102-1.494zh3.006zM5.748 7.502h9.504a.75.75 0 0 1 .102 1.494l-.102.006H5.748a.75.75 0 0 1-.102-1.493zh9.504z"></path></svg>
       <p>Noticias</p>
    </div>
    <div class="menuOpcion" id="1" onclick="cambiarContenido('jsp/centro.jsp', id)">
       <svg xmlns="http://www.w3.org/2000/svg" width="125" height="125"
          viewBox="0 0 24 24">
          <path fill="#a100ff"
             d="M21 10h-2V4h1V2H4v2h1v6H3a1 1 0 0 0-1 1v9h20v-9a1 1 0 0 0-1-1m-7 8v-4h-4v4H7V4h10v14z"></path>
          <path fill="#a100ff"
             d="M9 6h2v2H9zm4 0h2v2h-2zm-4 4h2v2H9zm4 0h2v2h-2z"></path></svg>
       <p>Centro</p>
    </div>
    <div class="menuOpcion" id="2" onclick="cambiarContenido('jsp/modulo.jsp', id)">
       <svg xmlns="http://www.w3.org/2000/svg" width="125" height="125"
          viewBox="0 0 24 24">
          <path fill="#a100ff"
             d="M7.5 22q-1.45 0-2.475-1.025T4 18.5v-13q0-1.45 1.025-2.475T7.5 2H20v15q-.625 0-1.062.438T18.5 18.5q0 .625.438 1.063T20 20v2zM6 15.325q.35-.175.725-.25T7.5 15H8V4h-.5q-.625 0-1.062.438T6 5.5zM10 15h8V4h-8zm-4 .325V4zM7.5 20h9.325q-.15-.35-.237-.712T16.5 18.5q0-.4.075-.775t.25-.725H7.5q-.65 0-1.075.438T6 18.5q0 .65.425 1.075T7.5 20"></path></svg>

       <p>Módulo</p>
    </div>
    <div class="menuOpcion" id="3" onclick="cambiarContenido('jsp/reservas.jsp', id)">
       <svg xmlns="http://www.w3.org/2000/svg" width="125" height="125"
          viewBox="0 0 24 24">
          <path fill="#a100ff"
             d="M5 22q-.825 0-1.412-.587T3 20V6q0-.825.588-1.412T5 4h1V3q0-.425.288-.712T7 2q.425 0 .713.288T8 3v1h8V3q0-.425.288-.712T17 2q.425 0 .713.288T18 3v1h1q.825 0 1.413.588T21 6v14q0 .825-.587 1.413T19 22zm0-2h14V10H5zm7-6q-.425 0-.712-.288T11 13q0-.425.288-.712T12 12q.425 0 .713.288T13 13q0 .425-.288.713T12 14m-4 0q-.425 0-.712-.288T7 13q0-.425.288-.712T8 12q.425 0 .713.288T9 13q0 .425-.288.713T8 14m8 0q-.425 0-.712-.288T15 13q0-.425.288-.712T16 12q.425 0 .713.288T17 13q0 .425-.288.713T16 14m-4 4q-.425 0-.712-.288T11 17q0-.425.288-.712T12 16q.425 0 .713.288T13 17q0 .425-.288.713T12 18m-4 0q-.425 0-.712-.288T7 17q0-.425.288-.712T8 16q.425 0 .713.288T9 17q0 .425-.288.713T8 18m8 0q-.425 0-.712-.288T15 17q0-.425.288-.712T16 16q.425 0 .713.288T17 17q0 .425-.288.713T16 18"></path></svg>
       <p>Tutorías</p>
    </div>
    <%
       }
    %><div class="menuOpcion" id="4" onclick="cambiarContenido('<%= pathCalificaciones %>', id)">
       <svg xmlns="http://www.w3.org/2000/svg" width="125" height="125"
          viewBox="0 0 24 24">
          <path fill="#a100ff"
             d="M21 16v-5.9l-8.05 4.375q-.45.25-.95.25t-.95-.25l-8.45-4.6q-.275-.15-.388-.375T2.1 9q0-.275.113-.5t.387-.375l8.45-4.6q.225-.125.463-.188T12 3.275q.25 0 .488.063t.462.187l9.525 5.2q.25.125.388.363T23 9.6V16q0 .425-.288.713T22 17q-.425 0-.712-.288T21 16m-9.95 4.475l-5-2.7q-.5-.275-.775-.75T5 16v-3.8l6.05 3.275q.45.25.95.25t.95-.25L19 12.2V16q0 .55-.275 1.025t-.775.75l-5 2.7q-.225.125-.462.188t-.488.062q-.25 0-.488-.062t-.462-.188"></path></svg>
       <p>Calificaciones</p>
    </div>
    <div class="menuOpcion" id="5" onclick="cambiarContenido('jsp/datosPersonales.jsp', id)">
       <svg xmlns="http://www.w3.org/2000/svg" width="125" height="125"
          viewBox="0 0 24 24">
          <path fill="#a100ff"
             d="M22 3H2c-1.09.04-1.96.91-2 2v14c.04 1.09.91 1.96 2 2h20c1.09-.04 1.96-.91 2-2V5a2.074 2.074 0 0 0-2-2m0 16H2V5h20zm-8-2v-1.25c0-1.66-3.34-2.5-5-2.5c-1.66 0-5 .84-5 2.5V17zM9 7a2.5 2.5 0 0 0-2.5 2.5A2.5 2.5 0 0 0 9 12a2.5 2.5 0 0 0 2.5-2.5A2.5 2.5 0 0 0 9 7m5 0v1h6V7zm0 2v1h6V9zm0 2v1h4v-1z"></path></svg>
       <p>Datos Personales</p>
    </div>
</div>
			<div class="cerrarSesion">
				<form action="./logout" method="POST">
					<input type="submit" value="Cerrar Sesión">
				</form>
			</div>
		</article>
		<div class="contenedorPrincipal">
			<iframe id="contenido" src="./jsp/inicio.jsp" width="100%" height="100%"></iframe>
		</div>
		</div>

		<footer>
			<p>
				Aplicación creada por <strong>Grupo La Rubia & co</strong>
			</p>
			<img src="./images/logoAccentureBlanco.png" alt="logoAccenture"
				 style="width: 8vw">
	</footer>
<script type="text/javascript" src="./scripts/script.js"></script>
</body>
</html>
