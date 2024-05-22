<%@ page import="models.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
         
<%@ page import="java.sql.*,utils.*,models.*" %>
<%@ page import="modelsDAO.CourseDAO" %>
<%@ page import="modelsDAO.NewDAO" %>
<%@ page import="modelsDAO.SchoolDAO" %>

<%
    User activeUser= (User) request.getSession().getAttribute("user");
    List<New> news = NewDAO.getAllNewsBySchoolId(activeUser.getId_school());
%>

<!DOCTYPE html>
<html>
<head>
    <link href="../styles/styleNoticias.css" rel="stylesheet">

    <meta charset="ISO-8859-1">
    <title>Insert title here</title>
</head>
<body onload="detColoresNoticias(<%=activeUser.getId_school()%>)">

<%
    for (New noticia : news) {
%>
    <div class="cont">
        <h1><%= noticia.getTitle() %></h1>
        <h3><%= noticia.getCaption() %></h3>

        <div class="imagen">
            <img alt="Imagen Noticia" class="imagen-noticia" src="<%= noticia.getImagen() %>">
            <div class="caption"><%= noticia.getCaptionImagen() %></div>
        </div>        
		<div class="linea">
            <hr>
        </div>
        <div class="desarrollo">
            <p><%= noticia.getContent() %></p>
        </div>
        <div class="fecha">
            <p><%= noticia.getCreationDate() %></p>
        </div>

    </div>

    <article>
        <div class="espacio">:D</div>
    </article>
<%
    }
%>

    <script type="text/javascript" src="../scripts/script.js"></script>
</body>
</html>
