<%@ page import="models.User"%>
<%@ page import="modelsDAO.UserDAO"%>
<%@ page import="models.Course"%>
<%@ page import="modelsDAO.CourseDAO"%>
<%@ page import="models.Subject"%>
<%@ page import="modelsDAO.SubjectDAO"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
User user = (User) request.getSession().getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="../styles/styleCalificaciones.css" rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap"
	rel="stylesheet">
<meta charset="UTF-8">
<title>Calificaciones</title>
</head>

<body onload="detColoresCalificaciones(<%=user.getSchool_id()%>)">
	<%
	if (user.getUserType().equals("02")) {
		String courses = UserDAO.getCourseFromTeacherId(user.getId());
		List<User> students;
		String[] coursesList = courses.split(";");
		Course course = CourseDAO.createCourse(user.getCourse_id());
	%>

	<h4>Calificaciones del centro escolar</h4>
	<div class="general">
		<div class="curso">
			<h2><%=UserDAO.getCourseFromTeacherId(user.getId())%></h2>
		</div>
		<div>
			<select class="asignaturas" name="cursoSelect" id="select"
				onchange="returnId()">
				<option value="0" disabled selected>-- Seleccione una
					asignatura --</option>
				<%
				List<Subject> subjects = SubjectDAO.getAllSubjectsByCourseId(user.getCourse_id());
				for (Subject subject : subjects) {
				%>
				<option value="<%=subject.getName()%>"><%=subject.getName()%></option>
				<%
				}
				%>
			</select>
		</div>
		<div>
			<select class="asignaturas" name="alumnosSelect" id="trimestre">
				<option value="0" disabled selected>-- Seleccione un
					trimestre --</option>
				<option value="1">1º Trimestre</option>
				<option value="2">2º Trimestre</option>
				<option value="3">3º Trimestre</option>
			</select>
		</div>
	</div>

	<div class="encabezados">
		<div class="alumnos">
			<h4>Alumnos</h4>
		</div>
		<div class="calificaciones">
			<h4>Calificaciones</h4>
		</div>
	</div>


	<div id="contenido">

		<div class="alumnoBox" id="box">
			<%
			students = UserDAO.getStudentsFromTeacherId(user.getId());

			for (User i : students) {
			%>
			<div>
				<input type="radio" name="student" value="<%=i.getId()%>"
					id="<%=i.getId()%>"> <label><%=i.getName()%></label>
			</div>
			<%
			}
			%>

		</div>
		<div id="frame">

			<div class="examen">
				<!-- 
					Aqui debe ir el bucle que recorra las notas que hay en ese trimestre, mediante el metodo DAO 
					que hay que hacer que devuelva la lista de notas de ese alumno en ese trimestre
					-->

				<button type="submit" name="Hola" class="boton">Prueba 1
					Nota 10</button>
				<button type="submit" name="Hola" class="boton">Prueba 1
					Nota 10</button>
				<button type="submit" name="Hola" class="boton">Prueba 1
					Nota 10</button>




			</div>

			<div class="anadir">
				<label>Nombre examen</label> <input type="text"> <label>Nota
					Examen</label> <input type="text">

				<button type="submit" name="anadir_examen" class="anadirbtn">Añadir
				</button>




			</div>


		</div>

	</div>
	<%
	}
	%>

	<script type="text/javascript" src="../scripts/script.js"></script>
	<script type="text/javascript" src="../scripts/calificaciones.js"></script>

</body>
</html>
