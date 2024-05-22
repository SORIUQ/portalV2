
let profesorSelect = document.getElementById('profesorSelect');
let contenido = document.getElementById('contenido');

// Añade un event listener para el evento 'change' del <select>
profesorSelect.addEventListener('change', function () {
    // Cambia el estilo display del <div> a 'block' para mostrar el contenido
    contenido.style.display = 'block';
});
