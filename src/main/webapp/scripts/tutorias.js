var profesorSelect = document.getElementById('profesorSelect');
var reservas = document.getElementsByClassName('reservas');
var opciones = document.getElementsByClassName('opciones');
var buttonInput = document.getElementById("buttonSubmit");

// Añade un event listener para el evento 'change' del <select>
buttonInput.addEventListener('click', function () {
    // Cambia el estilo display del <div> a 'block' para mostrar el contenido
    reservas.style.display = 'flex';
    opciones.style.display = 'flex';
    getTeacherID()
});

async function getStudents() {
    const teacherID = profesorSelect.value;
    const response = await fetch("../reservations?id=" + teacherID);
    const students = await response.json();
}