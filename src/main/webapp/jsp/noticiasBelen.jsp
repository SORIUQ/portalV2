<%@ page import="models.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    User activeUser= (User) request.getSession().getAttribute("user");
%>

<!DOCTYPE html>
<html>
    <head>
    <link href="../styles/styleNoticias.css" rel="stylesheet">

    <meta charset="UTF-8">
    <title>Insert title here</title>
    </head>
    <body onload="detColoresNoticias(<%=activeUser.getId_school()%>)">
        <div class="cont">
            <h1>Juan rescata a Fernando de la mafia del IES Belén</h1>
            <h3>Un acto heróico que marcó un antes y un después en los estudiantes</h3>

            <div class="imagen">
                <img alt="Juan Salvador" src="../images/imagenNoticiaBelen1.png">
                <div class="caption">Juan, icono actual del IES Belén</div>
            </div>
            <div class="linea">
                <hr>
            </div>
            <div class="desarrollo">
                <p>Juan Esteban Arboleda, un estudiante del Ciclo de Grado Superior en Diseño de Aplicaciones Multiplataforma ha realizado una azaña inolvidable para todos los estudiantes y personal del centro.</p>
                <p>Su compañero de clase, Fernando, se vio forzado hace unos años a enrolarse en la mafia más peligrosa de la región, la temida mafia del IES Belén. Este, ahogado por las deudas y en acto de desespero, decidió ir y pedir ayuda a estos.</p>
                <p>Para su mala suerte, estos tenían otros planes para él hasta que su compañero Juan, con el corazón en la mano, salvó a su amigo de las garras de la mafia.</p>
            </div>
        </div>
        <article>
			<div class="espacio">:D</div>
		</article>

        <script type="text/javascript" src="../scripts/script.js"></script>
    </body>
</html>
