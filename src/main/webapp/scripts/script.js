
function cambiarContenido(url, id) {
 
    let element;
    
    for (let i = 0; i < 6; i++){
        element = document.getElementById(i.toString());
        if (element!== null && element.classList.contains("selected")){
            element.classList.remove("selected");
        }
    }
 
    document.getElementById(id).classList.add("selected");
 
 
    let iframe = document.getElementById("contenido");
    iframe.src = url;
}
 
function irLogin() {
    window.location.href = "./jsp/login.jsp";
}

function addClassToElements(elementos, color) {
        for (var i = 0; i < elementos.length; i++) {
            elementos[i].classList.add(color);
        }
    }

function detColoresCentro(id){

    var color1 = document.getElementsByTagName("h4")[0];
    var color2 = document.getElementsByTagName("h4")[1];

    switch(id){
        case 1 :
            color1.classList.add('cesurColor');
            color2.classList.add('cesurColor');
            break;

        case 2 :
            color1.classList.add('picassoColor');
            color2.classList.add('picassoColor');
            break;

        case 3 :
            color1.classList.add('belenColor');
            color2.classList.add('belenColor');
            break;

        case 4 :
            color1.classList.add('turingColor');
            color2.classList.add('turingColor');
            break;

        case 5 :
            color1.classList.add('joseColor');
            color2.classList.add('joseColor');
            break;

        default : break;
    }
}  

function detColoresNoticias(id){

    var cont = document.getElementsByClassName("cont");
    var img= document.getElementsByTagName("img");
    var hr=document.getElementsByTagName("hr");

    var color;

    switch(id){
        case 1 :
            color = 'cesurColor';
            break;

        case 2 :
            color = 'picassoColor';
            break;

        case 3 :
            color = 'belenColor';
            break;

        case 4 :
            color = 'turingColor';
            break;

        case 5 :
            color = 'joseColor';
            break;

        default : break;
    }


    addClassToElements(cont, color);
    addClassToElements(img, color);
    addClassToElements(hr, color);

}
 
function determinarColores(id){
var headerElement = document.getElementsByTagName('header')[0];
var tarjetaElement = document.getElementsByClassName('tarjetaAlumno')[0];
var menuElement = document.getElementsByClassName('menu')[0];
var menuOpcionElements = document.getElementsByClassName('menuOpcion');
var cerrarElement = document.getElementsByClassName('cerrarSesion')[0];
	switch (id) {
  case 1:
    headerElement.classList.add('cesurColor');
    tarjetaElement.classList.add('cesurColor');
    menuElement.classList.add('cesurColor');
     for (var i = 0; i < menuOpcionElements.length; i++) {
                menuOpcionElements[i].classList.add('cesurColor');
            }
   
    cerrarElement.classList.add('cesurColor');
    
    break;
    
  case 2:
    headerElement.classList.add('picassoColor');
    tarjetaElement.classList.add('picassoColor');
    menuElement.classList.add('picassoColor');
         for (var i = 0; i < menuOpcionElements.length; i++) {
                menuOpcionElements[i].classList.add('picassoColor');
            }
           
    cerrarElement.classList.add('picassoColor');
   
    break;
    
  case 3:
    headerElement.classList.add('belenColor');
    tarjetaElement.classList.add('belenColor');
    menuElement.classList.add('belenColor');
         for (var i = 0; i < menuOpcionElements.length; i++) {
                menuOpcionElements[i].classList.add('belenColor');
            }
            
    cerrarElement.classList.add('belenColor');
    
    break;
    
  case 4:
    headerElement.classList.add('turingColor');
    tarjetaElement.classList.add('turingColor');
    menuElement.classList.add('turingColor');
         for (var i = 0; i < menuOpcionElements.length; i++) {
                menuOpcionElements[i].classList.add('turingColor');
            }
            
    cerrarElement.classList.add('turingColor');
    

    break;
    
  case 5:
	
    headerElement.classList.add('joseColor');
    tarjetaElement.classList.add('joseColor');
    menuElement.classList.add('joseColor');
         for (var i = 0; i < menuOpcionElements.length; i++) {
                menuOpcionElements[i].classList.add('joseColor');
            }
            
    cerrarElement.classList.add('joseColor');
    

    break;
    
    default : break;
	}
}
	
function detColorInicio(id){
	
  var elementoBorrar=document.getElementsByClassName("divider")[0];
		
			switch (id) {
  case 1:
    elementoBorrar.classList.add('cesurColor');
    break;
    
  case 2:
    elementoBorrar.classList.add('picassoColor');
    break;
    
  case 3:
    elementoBorrar.classList.add('belenColor');
    break;
    
  case 4:
    elementoBorrar.classList.add('turingColor');
    break;
    
  case 5:
    elementoBorrar.classList.add('joseColor');
    break;
    
    default : break;
	}
}



function detColoresCalificaciones(id){

    var h4 = document.getElementsByTagName("h4");
    var color;

    switch(id){
        case 1 :
            color = 'cesurColor';
            break;

        case 2 :
            color = 'picassoColor';
            break;

        case 3 :
            color = 'belenColor';
            break;

        case 4 :
            color = 'turingColor';
            break;

        case 5 :
            color = 'joseColor';
            break;

        default : break;
    }

    addClassToElements(h4, color);
}


function detColoresTutorias(id){

    var td = document.getElementsByTagName("td");
	var h4 = document.getElementsByTagName("h4");
	var boton = document.getElementsByClassName("btnComprobar");
	var ocupado = document.getElementsByClassName("ocupado");
	var botonReserva = document.getElementsByClassName("botonReserva");
	var Letras = document.getElementsByClassName("Letras");
	var buttonSubmit = document.getElementsByClassName("buttonSubmit");
	var profesorSelect = document.getElementsByClassName("profesorSelect");
    var color;

    switch(id){
        case 1 :
            color = 'cesurColor';
            break;

        case 2 :
            color = 'picassoColor';
            break;

        case 3 :
            color = 'belenColor';
            break;

        case 4 :
            color = 'turingColor';
            break;

        case 5 :
            color = 'joseColor';
            break;

        default : break;
    }

	addClassToElements(h4, color);
    addClassToElements(td, color);
    addClassToElements(ocupado, color);
    addClassToElements(botonReserva, color);
    addClassToElements(boton, color);
    addClassToElements(Letras, color);
    addClassToElements(buttonSubmit, color);
    addClassToElements(profesorSelect, color);
}

function detColoresModulo(id){

    var underline = document.getElementsByClassName("underline");
    var p = document.getElementsByTagName("p");
    var th = document.getElementsByTagName("th");
    var color;

    switch(id){
        case 1 :
            color = 'cesurColor';
            break;

        case 2 :
            color = 'picassoColor';
            break;

        case 3 :
            color = 'belenColor';
            break;

        case 4 :
            color = 'turingColor';
            break;

        case 5 :
            color = 'joseColor';
            break;

        default : break;
    }


    addClassToElements(underline, color);
    addClassToElements(p, color);
    addClassToElements(th, color);
}