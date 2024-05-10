function cambiarContenido(url, id) {

    let element;
    for (let i = 0; i < 6; i++){
        element = document.getElementById(i.toString());
        if (element.classList.contains("selected")){
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