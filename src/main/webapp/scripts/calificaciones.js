
let profesorSelect = document.getElementById('select');
let contenido = document.getElementById('box');
let selectrimestre =document.getElementById('trimestre');
let frame = document.getElementById('frame');
let booleano1 = false;
let booleano2 = false;


// Añade un event listener para el evento 'change' del <select>
profesorSelect.addEventListener('change', function () {
    // Cambia el estilo display del <div> a 'block' para mostrar el contenido
    booleano1 = true;
    if(booleano1 && booleano2){
		contenido.style.display = 'flex';
   		frame.style.display = 'flex';
	}
    
});

selectrimestre.addEventListener('change', function () {
    // Cambia el estilo display del <div> a 'block' para mostrar el contenido
    
    booleano2 = true;
    
	if(booleano1 && booleano2){
		contenido.style.display = 'flex';
   		frame.style.display = 'flex';
	}
    
});