
let profesorSelect = document.getElementById('select');
let contenido = document.getElementById('box');
let frame = document.getElementById('frame');
let btnElegir = document.getElementById('btnElegir');


// Añade un event listener para el evento 'change' del <select>
btnElegir.addEventListener('click', function () {
    // Cambia el estilo display del <div> a 'block' para mostrar el contenido


	contenido.style.display = 'flex';
	frame.style.display = 'flex';

    
});

selectrimestre.addEventListener('change', function () {
    // Cambia el estilo display del <div> a 'block' para mostrar el contenido
    
    booleano2 = true;
    
	if(booleano1 && booleano2){
		contenido.style.display = 'flex';
   		frame.style.display = 'flex';
	}
    
});