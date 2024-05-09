<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
		<form action="" method="post" class="login">
			<!-- NOMBRE -->
			<div class="input_container">
				<input type="text" name="user_name" required> <label>Nombre</label>
			</div>
			
			<!-- APELLIDOS -->
			<div class="input_container">
				<input type="text" name="user_lastname" required> <label>Apellidos</label>
			</div>
			
			<!-- EMAIL -->
			<div class="input_container">
				<input type="text" name="user_email" required> <label>Email</label>
			</div>
			
			<!-- FECHA NACIMIENTO -->
			<div class="input_container">
				<input type="date" class="date" name="user_birthday" required>
			</div>
			
			<!-- NIF -->
			<div class="input_container">
				<input type="text" name="user_nif" required> <label>NIF</label>
			</div>
			
			<!-- CENTRO -->
			<div class="input_container">
				<select name="user_center">
					<option value="0" disabled selected>-- Seleccione un centro --</option>
					<option value="1">CPIFP Alan Turing</option>
					<option value="2">SAN JOSE</option>
				</select>
			</div>
			
			<!-- CURSO -->
			<div class="input_container">
				<select name="user_course">
					<option value="0" disabled selected>-- Seleccione un curso --</option>
					<option value="1">DAM</option>
					<option value="2">DAW</option>
				</select>
			</div>
			
			<!-- CONTRASEÑA -->
			<div class="input_container">
				<input type="password" name="user_password" required> <label>Contraseña</label>
			</div>
			
			<!-- REPITE CONTRASEÑA -->
			<div class="input_container">
				<input type="password" name="user_password2" required> <label>Repetir contraseña</label>
			</div>
			
			<!-- BOTÓN REGISTRO -->
			<a type="submit" class="register-button container-button">Registrar</a>

			<!-- LOGIN LINK -->
			<div class="account-animation">
				<a href="./login.jsp" class="animated-link"> Tengo una cuenta creada</a>
			</div>
		</form>
	</div>

	<!-- FOOTER -->
	<footer>
		<p>Aplicación creada por <strong>Grupo La Rubia & co</strong></p>
	</footer>
</body>
</html>