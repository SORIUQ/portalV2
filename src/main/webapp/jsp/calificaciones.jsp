<%@ page import="models.*"%>
<%@ page import="modelsDAO.UserDAO"%>
<%@ page import="models.Subject"%>
<%@ page import="modelsDAO.SubjectDAO"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map" %>
<%@ page import="modelsDAO.CourseDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
User user = (User) request.getSession().getAttribute("user");
	if (user.getUserType().equals("01")) {
		response.sendRedirect("./calificacionesAlumno.jsp");
	}
	String subjectSelected = (String) request.getSession().getAttribute("subjectSelected");
	List <User> userSubject = (List<User>) session.getAttribute("subjectStudents");
	List <Grade> gradesSubjectList = (List<Grade>) session.getAttribute("gradesList");
	Map<String,String> alumnoElegido = null;
	if (session.getAttribute("studentSelected") != null)
		alumnoElegido = UserDAO.getUserInfo((Integer)session.getAttribute("studentSelected"));
	Double notaMedia = 0.0;
	if (gradesSubjectList != null) {
		for (Grade item : gradesSubjectList){
			notaMedia += Double.valueOf(String.valueOf(item.getGrade()));
		}
		notaMedia = notaMedia/gradesSubjectList.size();
	}
	String teacherGradesError = (String) session.getAttribute("teacherGradesError");
	
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
	<h4>Calificaciones del centro escolar</h4>
	<div class="general">
		<div class="subjectForm">
			<form method="GET" action="../teacherGrades">
				<select class="asignaturas" name="subjectSelected" id="select">
					<option value="0" disabled selected>-- Seleccione una asignatura --</option>
					<%List<Subject> subjects = SubjectDAO.getAllSubjectsByUser(user);
					for (Subject subject : subjects) {%>
						<option value="<%=subject.getSubjectId()%>"><%=subject.getName()%></option>
					<%}%>
				</select>
				<input type="submit" value="Seleccionar">
			</form>
		</div>
				<div class="curso">
			<% if (subjectSelected != null) {%>
			<h3>Notas de <%=subjectSelected%></h3>
			<%}else if (teacherGradesError != null) {%>
				<h3><%=teacherGradesError%></h3>
			<%}%>
		</div>
	</div>

	<div class="encabezados">
		<div class="alumnos">
			<h4>Alumnos</h4>
			<form method="GET" action="../showGrades">
				<%if(userSubject != null) {
				for(User item : userSubject) {
				Map<String,String> infoUser = UserDAO.getUserInfo(item.getId());%>
				<div class="alumnosOptions">
				<input id="<%=item.getId()%>" type="radio" value="<%=item.getId()%>" name="studentSelected">
				<label for="<%=item.getId()%>"> <%=infoUser.get("surname")%>, <%=infoUser.get("name")%></label><br>
				</div><%}
				}%>
				<br><input type="submit" id="btnElegir" value="Elegir Alumno">
			</form>
		</div>

		<div class="calificaciones">
			<h4>Calificaciones</h4>
			<div id="frame">
			<%if (alumnoElegido != null) {%>
			<h3><%=alumnoElegido.get("name")%> <%=alumnoElegido.get("surname")%></h3>
				<div id="contenido">
					<%if (gradesSubjectList != null) {%>
						<table>
						<%for (int i = 0; i < gradesSubjectList.size(); i++) {
							Grade item = gradesSubjectList.get(i);%>
						<tr id=<%="nota" + item.getStudent_id() + i%>>
							<td class="deleteCell">
								<form action="../deleteGrade?gradeTeacherId=<%=item.getTeacher_id()%>&gradeStudentId=<%=item.getStudent_id()%>&gradeSubjectId=<%=item.getSubject_id()%>&gradeDescription=<%=item.getGrade_desc()%>&grade=<%=item.getGrade()%>" method="POST">
									<button class="deleteNote" value="">❌</button>
								</form>
							</td>
							<td class="infoGrade"><%=item.getGrade_desc()%></td>
							<td class="numberGrade"><%=item.getGrade()%></td>
						</tr>
						<%}%>
						</table>
					<%}%>
			    </div>
				<div class="parteBaja">
					<p> <b>NOTA MEDIA DE LA ASIGNATURA : <%=notaMedia%></b></p>
					<div class="anadir">
						<form method="post" action="../showGrades">
							<label>Nombre examen</label>
							<input type="text" name="grade_description" required>
							<label>Nota Examen</label>
							<input type="number" name="gradeNumber" min="0" max="10" value="5.0" step="0.01" required>
							<button type="submit" name="anadir_examen" class="anadirBtn">Añadir</button>
						</form>
					</div>
				</div>
		    </div>
			<%}%>
		</div>
	</div>

	<script type="text/javascript" src="../scripts/script.js"></script>
	<!--<script type="text/javascript" src="../scripts/calificaciones.js"></script> -->

</body>
</html>
