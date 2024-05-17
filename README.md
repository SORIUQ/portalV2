# Portal de Accenture
![](src/main/webapp/images/logos/LOGOTIPO-ACCENTURE.png)
## Funcionamiento del repositorio

> [!NOTE]
> Los encargados del repositorio son **Ricardo y Pedro**.  
> Cualquier duda con el repositorio, consultar con alguno de los dos.

> [!WARNING]
> Es obligatorio hacer un fork al repositorio para trabajar con **pull request**.  
> De esta manera, evitamos cambios sin supervisar y podemos resolver los conflictos más fácilmente.

## Funcionamiento del proyecto
El portal está dividido en diferentes partes:

* **Inicio de sesión**  
  - Para entrar dentro del portal, debes pasar por un login donde debes introducir un correo electrónico y una contraseña asociada.  
  - La contraseña y el correo electrónico deben estar registrados en la base de datos para poder acceder al portal.

* **Registro de un alumno**  
  - Un alumno puede llevar a cabo un registro de usuario introduciendo la información requerida.  
  - Necesitas introducir nombre, apellidos, email, fecha de nacimiento, NIF, centro de estudios, módulo del curso, contraseña y confirmación de la contraseña.

* **Página principal**  
  - La página principal se divide en diferentes apartados que varían dependiendo del tipo de usuario que acceda.  
  
    - Un usuario **Accenture** podrá ver únicamente las calificaciones y sus propios datos personales.  
    - Un usuario **Profesor** podrá ver los apartados de noticias, centro, módulo, tutorías, calificaciones y datos personales. Además, tendrá varios privilegios de profesor dentro de la página.  
    - Un usuario **Alumno** podrá ver los mismos apartados que el profesor, pero tendrá diferentes privilegios en los diferentes apartados.
   
* **Apartados de la página principal**
  - ***Portal de la página***
    - Dependiendo del centro en el que te encuentres, cambiará el logo y el nombre del centro. Por otro lado, cambiarán los colores de la página adaptándose a los colores del logo de tu centro.
    - En la esquina superior derecha, veremos una tarjeta con el nombre del usuario y el curso en el que se encuentra.
  - ***Noticias***
    - Las noticias de cada centro variarán dependiendo del usuario que esté loggeado.  
      **WIP:** Las noticias se encuentran en la base de datos y accedemos a ellas de manera dinámica, facilitando el mantenimiento y la creación de nuevas noticias.
  - ***Centro***
    - Dentro de centro, podemos ver información del centro del usuario.
    - Podemos ver el número de contacto del centro, email, horarios de secretaría, municipio y la localización en Google Maps del centro.
  - ***Módulo***
    - **WIP**
  - ***Tutorías***
    - **WIP**
  - ***Calificaciones***
    - **WIP**
  - ***Datos personales***
    - En datos personales, podemos ver el nombre, apellidos, NIF y la fecha de nacimiento del usuario.
    - Además, el color de la tarjeta de los datos del usuario también varía dependiendo del centro en el que nos encontremos.
