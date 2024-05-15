



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

function determinarColores(id){
	
var headerElement = document.getElementsByTagName('header')[0];
var tarjetaElement = document.getElementsByClassName('tarjetaAlumno')[0];
var menuElement = document.getElementsByClassName('menu')[0];
var menuOpcionElements = document.getElementsByClassName('menuOpcion');
var cerrarElement = document.getElementsByClassName('cerrarSesion')[0];
var selectedElement = document.getElementsByClassName('selected');

	switch (id) {
  case 1:
    headerElement.classList.add('cesurColor');
    tarjetaElement.classList.add('cesurColor');
    menuElement.classList.add('cesurColor');
     for (var i = 0; i < menuOpcionElements.length; i++) {
                menuOpcionElements[i].classList.add('cesurColor');
            }
   
    cerrarElement.classList.add('cesurColor');
    
     for (var i = 0; i < selectedElement.length; i++) {
                selectedElement[i].classList.add('cesurColor');
            }
    break;
    
  case 2:
    headerElement.classList.add('picassoColor');
    tarjetaElement.classList.add('picassoColor');
    menuElement.classList.add('picassoColor');
         for (var i = 0; i < menuOpcionElements.length; i++) {
                menuOpcionElements[i].classList.add('picassoColor');
            }
            
    cerrarElement.classList.add('picassoColor');
    
         for (var i = 0; i < selectedElement.length; i++) {
                selectedElement[i].classList.add('picassoColor');
            }
    break;
    
  case 3:
    headerElement.classList.add('belenColor');
    tarjetaElement.classList.add('belenColor');
    menuElement.classList.add('belenColor');
         for (var i = 0; i < menuOpcionElements.length; i++) {
                menuOpcionElements[i].classList.add('belenColor');
            }
            
    cerrarElement.classList.add('belenColor');
    
         for (var i = 0; i < selectedElement.length; i++) {
                selectedElement[i].classList.add('belenColor');
            }
    break;
    
  case 4:
    headerElement.classList.add('turingColor');
    tarjetaElement.classList.add('turingColor');
    menuElement.classList.add('turingColor');
         for (var i = 0; i < menuOpcionElements.length; i++) {
                menuOpcionElements[i].classList.add('turingColor');
            }
            
    cerrarElement.classList.add('turingColor');
    
         for (var i = 0; i < selectedElement.length; i++) {
                selectedElement[i].classList.add('turingColor');
            }
    break;
    
  case 5:
	
    headerElement.classList.add('joseColor');
    tarjetaElement.classList.add('joseColor');
    menuElement.classList.add('joseColor');
         for (var i = 0; i < menuOpcionElements.length; i++) {
                menuOpcionElements[i].classList.add('joseColor');
            }
            
    cerrarElement.classList.add('joseColor');
    
        for (var i = 0; i < selectedElement.length; i++) {
                selectedElement[i].classList.add('joseColor');
            }
    break;
    
    default : break;
	}

}