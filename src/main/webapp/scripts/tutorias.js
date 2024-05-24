var profesorSelect = document.getElementById('profesorSelect');
var reservas = document.getElementById('reservas');
var opciones = document.getElementById('opciones');
var buttonInput = document.getElementById("buttonSubmit");

if (reservas && opciones) {
    reservas.style.display = 'none';
    opciones.style.display = 'none';
}

// Añade un event listener para el evento 'change' del <select>
buttonInput.addEventListener('click', function () {
    // Cambia el estilo display del <div> a 'block' para mostrar el contenido
    reservas.style.display = 'flex';
    opciones.style.display = 'flex';
});