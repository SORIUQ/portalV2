var profesorSelect = document.getElementById('profesorSelect');
var reservas = document.getElementsByClassName('reservas');
var opciones = document.getElementsByClassName('opciones');
var buttonInput = document.getElementById("buttonSubmit");

// Añade un event listener para el evento 'change' del <select>
buttonInput.addEventListener('click', function () {
    // Cambia el estilo display del <div> a 'block' para mostrar el contenido
    reservas.classList.toggle("show")
    opciones.classList.toggle("show")
});

document.addEventListener('DOMContentLoaded', function() {
    const reservas = document.querySelectorAll('.libre'); 
    let selectedReserva = null;

    reservas.forEach(reserva => {
        reserva.addEventListener('click', function() {
            reservas.forEach(r => r.classList.remove('selected'));
            
            this.classList.add('selected');
            selectedReserva = this.getAttribute('data-id');
            console.log('Reserva seleccionada:', selectedReserva); // Para depuración
        });
    });
});

function confirmReserva() {
    const selectedReservaInput = document.getElementById('selectedReserva');
    
    if (selectedReserva) {
        selectedReservaInput.value = selectedReserva;
        document.getElementById('confirmForm').submit();
    } else {
        alert('Por favor, seleccione una reserva antes de confirmar.');
    }
}