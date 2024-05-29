<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Jost:ital,wght@0,100..900;1,100..900&display=swap"
            rel="stylesheet">
        <link href="../styles/styleLogin.css" rel="stylesheet">
        <link rel="icon" type="image/x-icon" href="../images/accFavicon.jpg">
        <meta charset="UTF-8">
        <title>Login</title>
    </head>

    <body>
        <div class="contenedorLogoNombre">
            <img src="../images/logoAccenture.svg" alt="logoAccenture">
            <h1>Grupo La Rubia & co</h1>
        </div>
        <div class="contenedorLogin">
        <br>
                <form action="../login" method="POST">
						
                    <div class="input-field">
                        <input id="input1" type="text" name="email" required> <label for="input1">Email</label>
                    </div>
                    <br>
                    <div class="input-field">
                        <input id="input2" type="password" name="password" required> <label for="input2">Contraseña</label>
                    </div>
                    <br>
                    <%if (session.getAttribute("errorMsgLogin") != null) {%>
                        <p class="errorMsg"><%=session.getAttribute("errorMsgLogin")%></p>
                        <%session.setAttribute("errorMsg",null);
                    }%>
                    <%if (session.getAttribute("userExists") != null) {%>
                        <p class="errorMsg"><%=session.getAttribute("userExists")%></p>
                        <%session.setAttribute("userExists",null);
                        }%>
                    <%if (session.getAttribute("userRegistered") != null) {%>
                        <p class="okMsg"><%=session.getAttribute("userRegistered")%></p>
                        <%session.setAttribute("userRegistered",null);
                    }%>
                    <div class="input-submit">
                        <input type="submit" value="Acceder">
                    </div>
                    <br>
                    <div class="enlaceRegistro"> 
                    <a href="./register.jsp">Acceder a registro de alumnos</a>
                	</div>
                </form>
                <br>
        </div>
    </body>

    </html>
