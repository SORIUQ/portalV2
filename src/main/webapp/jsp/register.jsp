<%@ page import="models.Course"%>
<%@ page import="models.School"%>
<%@ page import="java.util.List"%>
<%@ page import="modelsDAO.SchoolDAO"%>
<%@ page import="modelsDAO.CourseDAO"%>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	List<School> schools = SchoolDAO.getAllSchools();
	List<Course> courses = CourseDAO.getAllCourses();
	Map<Integer, List<Course>> coursesMap = CourseDAO.getAllCoursesOfSchool();
	Gson gson = new Gson();
	String coursesMapJson = gson.toJson(coursesMap);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1" name="viewport"
	content="width=device-width, initial-scale=1.25, maximum-scale=1">
<!-- CSS -->
<link href="../styles/styleRegister.css" rel="stylesheet">
<!-- FONTS -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap"
	rel="stylesheet">
<!-- FAVICON -->
<link rel="icon" type="image/x-icon" href="../images/accFavicon.jpg">
<title>Register</title>
</head>

<body>
	<div id="mainContainer">
		<!-- ACCENTURE IMAGE -->
		<img src="../images/logoAccenture.svg" id="logoAccenture">

		<!-- FORM -->
		<form action="../register" method="post" class="login"
			onsubmit=" return userValidation()">

			<!-- NOMBRE -->
			<div class="input_container" id="nameContainer">
				<input type="text" name="user_name" id="nameInput" required>
				<label>Nombre</label>
			</div>

			<!-- APELLIDOS -->
			<div class="input_container" id="lastNameContainer">
				<input type="text" name="user_lastname" id="lastNameInput" required>
				<label>Apellidos</label>
			</div>

			<!-- EMAIL -->
			<div class="input_container" id="emailContainer">
				<input type="text" name="user_email" id="emailInput" required>
				<label>Email</label>
				<%
				if (session.getAttribute("emailExists") != null) {
				%>
				<p class="errorMsg">
					<%=session.getAttribute("emailExists")%>
				</p>
				<%
				}
				%>
			</div>

			<!-- FECHA NACIMIENTO -->
			<div class="input_container">
				<input type="date" class="date" name="user_birthday"
					max="2008-05-15" min="1950-01-01" required>
			</div>

			<!-- NIF -->
			<div class="input_container" id="dnieContainer">
				<input type="text" name="user_nif" id="dnieInput" required>
				<label>NIF</label>
				<%
				if (session.getAttribute("dniExists") != null) {
				%>
				<p class="errorMsg">
					<%=session.getAttribute("dniExists")%>
				</p>
				<%
				}
				%>
			</div>

			<!-- CENTRO -->
			<div class="input_container">
				<select name="user_center" id="user_center" onchange="updateOptions(this.value)">
					<option value="0" disabled selected>-- Seleccione un centro --</option>
					<%for (School s : schools) {%>
						<option value="<%=s.getIdSchool()%>"><%=s.getSchoolName()%></option>
					<% } %>
				</select>
			</div>

			<!-- CURSO -->
			<div class="input_container" id="courseContainer">
				<select name="user_course" id="courseInput" required>
					<option value="0" disabled selected>-- Seleccione un curso --</option>
				</select>
			</div>

			<!-- CONTRASEÑA -->

			<div class="input_container" id="passContainer">
				<input type="password" name="user_password" id="passInput"
					minlength="8" required> <label>Contraseña</label>
			</div>

			<!-- REPITE CONTRASEÑA -->
			<div class="input_container" id="pass2Container">
				<input type="password" name="user_password2" id="passInput2"
					minlength="8" required> <label>Repetir contraseña</label>
			</div>

			<!-- BOTÓN REGISTRO -->
			<button type="submit" class="register-button container-button">Registrar</button>

			<!-- LOGIN LINK -->
			<div class="account-animation">
				<a href="./login.jsp" class="animated-link"> Tengo una cuenta
					creada</a>
			</div>
		</form>
	</div>

	<!-- FOOTER -->
	<footer>
		<p>
			Aplicación creada por <strong>Grupo La Rubia & co</strong>
		</p>
	</footer>
	<script src="../scripts/register.js"></script>
	<script>
		const coursesMap = <%= coursesMapJson %>;
		const updateOptions = (schoolID) => {
			const schoolSelect = document.getElementById('user_center');
			const courseSelect = document.getElementById('courseInput');
			courseSelect.innerHTML = '';
			const selectedCourses = coursesMap[schoolID];
			if (selectedCourses) {
				for (const course of selectedCourses) {
					const option = document.createElement('option');
					option.value = course.id_course; // Assign the course ID to the value attribute
					option.text = course.nameCourse; // Assign the course name to the option text
					courseSelect.appendChild(option);
				}
			}
		};
		let validationResults = {};
		function userValidation() {
			validationResults = {
				name: checkNameInput(),
				lastName: checkLastNameInput(),
				email: checkEmailInput(),
				dnie: checkDnieInput(),
				password: checkPassInput(),
				password2: checkPass2Input()
			};
			return Object.values(validationResults).every(result => result);
		}
	</script>
</body>
</html>
